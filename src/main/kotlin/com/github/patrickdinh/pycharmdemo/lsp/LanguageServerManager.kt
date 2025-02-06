package com.github.patrickdinh.pycharmdemo.lsp

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import java.nio.charset.StandardCharsets

class LanguageServerManager: LspServerSupportProvider {
    override fun fileOpened(
        project: Project,
        file: VirtualFile,
        serverStarter: LspServerSupportProvider.LspServerStarter
    ) {
        if (file.extension == "py") {
            serverStarter.ensureServerStarted(PyLspServerDescriptor(project))
        }
    }
}

class PyLspServerDescriptor(project: Project): ProjectWideLspServerDescriptor(project, "Python LSP") {
    override fun isSupportedFile(file: VirtualFile) = file.extension == "py"

    override fun createCommandLine(): GeneralCommandLine {
        return GeneralCommandLine()
            .withCharset(StandardCharsets.UTF_8)
            .withExePath("node")
            .withParameters(getServerPath(), "--stdio")
    }

    private fun getServerPath(): String {
        // This should point to your server.js file
        return "C:/dev/vscode-extension-samples/lsp-sample/server/out/server.js"
    }
}

