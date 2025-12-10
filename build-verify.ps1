# Build Verification Script
# Verifies both frontend and backend builds

# Use script directory instead of hardcoded path
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptPath

Write-Host "=== BUILD VERIFICATION ===" -ForegroundColor Cyan
Write-Host ""

# ========== FRONTEND BUILD ==========
Write-Host ">>> FRONTEND BUILD" -ForegroundColor Yellow
Write-Host "Building frontend..." -ForegroundColor Gray

cd frontend

$frontendBuild = npm run build 2>&1
$frontendStatus = $LASTEXITCODE

Write-Host ""
Write-Host "Last 15 lines of build output:" -ForegroundColor Gray
$output_lines = ($frontendBuild | Measure-Object -Line).Lines
$start_line = [Math]::Max(0, $output_lines - 15)
$frontendBuild | Select-Object -Last 15

if ($frontendStatus -eq 0) {
    Write-Host "✓ Frontend build: SUCCESS" -ForegroundColor Green
} else {
    Write-Host "✗ Frontend build: FAILED (exit code: $frontendStatus)" -ForegroundColor Red
}

Write-Host ""

# ========== BACKEND BUILD ==========
Write-Host ">>> BACKEND BUILD" -ForegroundColor Yellow
Write-Host "Building backend..." -ForegroundColor Gray

cd ..

$backendBuild = .\mvnw.cmd clean compile -q 2>&1
$backendStatus = $LASTEXITCODE

if ($backendStatus -eq 0) {
    Write-Host "✓ Backend compile: SUCCESS" -ForegroundColor Green
} else {
    Write-Host "✗ Backend compile: FAILED (exit code: $backendStatus)" -ForegroundColor Red
    Write-Host "Error output:" -ForegroundColor Gray
    Write-Host $backendBuild
}

Write-Host ""
Write-Host "=== VERIFICATION COMPLETE ===" -ForegroundColor Cyan
Write-Host ""

# ========== SUMMARY ==========
Write-Host "REPORT:" -ForegroundColor Cyan
Write-Host "1. Frontend build status: $(if ($frontendStatus -eq 0) { 'SUCCESS ✓' } else { 'FAILED ✗' })" -ForegroundColor $(if ($frontendStatus -eq 0) { 'Green' } else { 'Red' })
Write-Host "2. Backend compile status: $(if ($backendStatus -eq 0) { 'SUCCESS ✓' } else { 'FAILED ✗' })" -ForegroundColor $(if ($backendStatus -eq 0) { 'Green' } else { 'Red' })

if ($frontendStatus -ne 0 -or $backendStatus -ne 0) {
    Write-Host "3. Errors: YES - Review output above" -ForegroundColor Red
    exit 1
} else {
    Write-Host "3. Errors: NONE" -ForegroundColor Green
    exit 0
}