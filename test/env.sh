#!/bin/bash
# Shared settings of the test scripts. The other scripts load this file.
# Do not run it by itself.

ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)

# All build files and data files go into this folder. Git ignores it.
OUT="$ROOT/test-build"
CLASSES="$OUT/classes"
TESTCLASSES="$OUT/test-classes"

# On Windows (Git Bash) Java needs Windows paths and ";" between paths
if command -v cygpath > /dev/null 2>&1; then
    winpath() { cygpath -w "$1"; }
    SEP=";"
else
    winpath() { echo "$1"; }
    SEP=":"
fi

if ! command -v javac > /dev/null 2>&1; then
    echo "javac was not found. Install a JDK and put it on the PATH."
    exit 1
fi
