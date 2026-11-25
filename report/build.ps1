# Compile report/main.tex to report/build/main.pdf and copies it to
# report/build/<name>_<date>_<commit>.pdf.
# It also writes a docx copy with the same name, made with pandoc.
# The docx holds the text only (for plagiarism checkers). Diagrams are not included.
# reference.docx sets the fonts (Times New Roman).
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
$base = "Courier-and-Parcel-Tracking-Simulator_Project-Report_Group-8_KGEC_2026-2027_$(Get-Date -Format yyyy-MM-dd)_$sha"
Copy-Item build\main.pdf "build\$base.pdf" -Force
Write-Host "PDF written to $PSScriptRoot\build\$base.pdf"

if (Get-Command pandoc -ErrorAction SilentlyContinue) {
    pandoc main.tex -f latex --reference-doc=reference.docx -o "build\$base.docx"
    if ($LASTEXITCODE -eq 0) { Write-Host "DOCX written to $PSScriptRoot\build\$base.docx" }
    else { Write-Host "pandoc failed. No docx written." }
} else {
    Write-Host "pandoc not found. No docx written."
}
