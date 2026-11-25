# Compile report/main.tex to report/build/main.pdf and copies it to
# report/build/<name>_<date>_<commit>.pdf.
# Uses latexmk when it works. If latexmk fails (for example, Perl is missing),
# it runs pdflatex three times instead, which also resolves the references.
Set-Location $PSScriptRoot

latexmk -pdf -interaction=nonstopmode -halt-on-error -outdir=build main.tex
if ($LASTEXITCODE -ne 0) {
    Write-Host "latexmk failed. Falling back to pdflatex."
    New-Item -ItemType Directory -Force build | Out-Null
    foreach ($i in 1..3) {
        pdflatex -interaction=nonstopmode -halt-on-error -output-directory=build main.tex
        if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
    }
}
$sha = (git rev-parse --short=7 HEAD 2>$null)
if (-not $sha) { $sha = "nogit" }
$name = "Courier-and-Parcel-Tracking-Simulator_Project-Report_Group-8_KGEC_2026-2027_$(Get-Date -Format yyyy-MM-dd)_$sha.pdf"
Copy-Item build\main.pdf "build\$name" -Force
Write-Host "PDF written to $PSScriptRoot\build\$name"
