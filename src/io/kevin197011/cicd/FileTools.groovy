package io.kevin197011.cicd

def inputGetFile(String destFile = null ) {
    def fdata = null
    def fname = null

    if (destFile == null) {
        def inputFile = input message: 'Upload file', parameters: [file(name: 'fdata'), string(name: 'fname', defaultValue: 'app.jar')]
        fdata = inputFile['fdata']
        fname = inputFile['fname']
    } else {
        def inputFile = input message: 'Upload file', parameters: [file(name: 'fdata')]
        filedata = inputFile
        filename = destFile
    }

    writeFile(file: fname, encoding: 'Base64', text: fdata.read().getBytes().encodeBase64().toString())
    fdata.delete()
    return fname
}