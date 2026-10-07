package com.ct.explorer

import com.ct.explorer.data.model.FileItem
import com.ct.explorer.utils.ArchiveHelper
import com.ct.explorer.utils.HashCalculator
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class ArchiveAndFileSecurityTest {

    @get:Rule
    val tempFolder = TemporaryFolder()

    @Test
    fun testFileItemByteFormatting() {
        assertEquals("0 B", FileItem.formatBytes(0L))
        assertEquals("1.0 KB", FileItem.formatBytes(1024L))
        assertEquals("1.0 MB", FileItem.formatBytes(1024L * 1024L))
        assertEquals("1.50 GB", FileItem.formatBytes((1.5 * 1024L * 1024L * 1024L).toLong()))
    }

    @Test
    fun testHashCalculatorProducesAccurateChecksums() = runBlocking {
        val testFile = tempFolder.newFile("sample_hash_test.txt")
        testFile.writeText("CentFileManagerTestString123")

        val result = HashCalculator.calculateHashes(testFile)
        assertTrue(result.isSuccess)
        val hashes = result.getOrNull()
        assertNotNull(hashes)
        assertEquals(28L, hashes?.fileSize)
        assertFalse(hashes?.md5.isNullOrBlank())
        assertFalse(hashes?.sha1.isNullOrBlank())
        assertFalse(hashes?.sha256.isNullOrBlank())
    }

    @Test
    fun testZipSlipPathTraversalIsBlocked() = runBlocking {
        val maliciousZip = tempFolder.newFile("malicious.zip")
        ZipOutputStream(FileOutputStream(maliciousZip)).use { zos ->
            // Try to write outside the extraction destination
            zos.putNextEntry(ZipEntry("../evil_exploit.txt"))
            zos.write("malicious payload".toByteArray())
            zos.closeEntry()
        }

        val extractDest = tempFolder.newFolder("safe_extract_dir")
        val extractResult = ArchiveHelper.extractArchive(
            file = maliciousZip,
            destDir = extractDest
        ) { _, _ -> }

        // Must fail with a SecurityException detecting Zip Slip
        assertTrue(extractResult.isFailure)
        val exception = extractResult.exceptionOrNull()
        assertTrue(exception is SecurityException)
        assertTrue(exception?.message?.contains("Zip Slip") == true)
    }

    @Test
    fun testNormalArchiveExtractsSuccessfully() = runBlocking {
        val normalZip = tempFolder.newFile("normal.zip")
        ZipOutputStream(FileOutputStream(normalZip)).use { zos ->
            zos.putNextEntry(ZipEntry("documents/hello.txt"))
            zos.write("hello world from cent file manager".toByteArray())
            zos.closeEntry()
        }

        val extractDest = tempFolder.newFolder("extracted_normal")
        val extractResult = ArchiveHelper.extractArchive(
            file = normalZip,
            destDir = extractDest
        ) { _, _ -> }

        assertTrue(extractResult.isSuccess)
        val extractedFile = File(extractDest, "documents/hello.txt")
        assertTrue(extractedFile.exists())
        assertEquals("hello world from cent file manager", extractedFile.readText())
    }
}
