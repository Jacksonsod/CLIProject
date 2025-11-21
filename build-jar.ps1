$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$srcDir = Join-Path $projectRoot "CLI"
$outDir = Join-Path $projectRoot "out\classes"
$libDir = Join-Path $projectRoot "CLI\lib"

if (-not (Test-Path $outDir)) {
    New-Item -ItemType Directory -Path $outDir -Force | Out-Null
}

$javaFiles = Get-ChildItem -Path $srcDir -Recurse -Filter *.java | Select-Object -ExpandProperty FullName

if (-not $javaFiles) {
    Write-Error "No .java files found under $srcDir"
    exit 1
}

$cp = "$libDir\mysql-connector-java-8.0.28.jar"

javac -d $outDir -cp $cp $javaFiles
if ($LASTEXITCODE -ne 0) {
    Write-Error "javac failed with exit code $LASTEXITCODE"
    exit $LASTEXITCODE
}

$manifest = Join-Path $projectRoot "manifest.mf"
if (-not (Test-Path $manifest)) {
    Write-Error "manifest.mf not found in $projectRoot"
    exit 1
}

$jarPath = Join-Path $projectRoot "CLIUserManager.jar"

& "C:\Program Files\Java\jdk-25\bin\jar.exe" cfm $jarPath $manifest -C $outDir .
if ($LASTEXITCODE -ne 0) {
    Write-Error "jar failed with exit code $LASTEXITCODE"
    exit $LASTEXITCODE
}

Write-Host "Built JAR: $jarPath"
