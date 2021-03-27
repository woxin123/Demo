package top.mcwebsite.kotlin.demo.base.annotion

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class HasTempFolder {

    /**
     * org.junit.internal.runners.rules.ValidationError: The @Rule 'folder' must be public.
     * @Rule
     * val folder = TemporaryFolder()
     */

    @get:Rule
    val folder = TemporaryFolder()

    @Test
    fun testUsingTempFolder() {
        val createFile = folder.newFile("myfile.txt")
        val createFolder = folder.newFolder("subfolder")
        // ...
    }

}