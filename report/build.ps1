# Compile report/main.tex to report/build/main.pdf.
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
Write-Host "PDF written to $PSScriptRoot\build\main.pdf"
