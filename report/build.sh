#!/usr/bin/env bash
# Compile report/main.tex to report/build/main.pdf and copies it to
# report/build/<name>_<date>_<commit>.pdf.
# Uses latexmk when it works. If latexmk fails (for example, Perl is missing),
# it runs pdflatex three times instead, which also resolves the references.
set -euo pipefail
cd "$(dirname "$0")"

if ! latexmk -pdf -interaction=nonstopmode -halt-on-error -outdir=build main.tex; then
  echo "latexmk failed. Falling back to pdflatex."
  mkdir -p build
  for _ in 1 2 3; do
    pdflatex -interaction=nonstopmode -halt-on-error -output-directory=build main.tex
  done
fi
NAME="Courier-and-Parcel-Tracking-Simulator_Project-Report_Group-8_KGEC_2026-2027_$(date +%Y-%m-%d)_$(git rev-parse --short=7 HEAD 2>/dev/null || echo nogit).pdf"
cp build/main.pdf "build/$NAME"
echo "PDF written to $(pwd)/build/$NAME"
