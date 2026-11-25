#!/bin/bash
# Builds the project and runs all unit tests with one call.
# Usage: bash test/run-tests.sh
# It needs javac and java on the PATH. It stops at the first failing step.
#
# The GitHub workflow runs the same three scripts, one step each:
#   1. bash test/build.sh          build test
#   2. bash test/compile-tests.sh  compile the test classes
#   3. bash test/unit-tests.sh     run the unit tests

DIR=$(dirname "$0")

for step in build.sh compile-tests.sh unit-tests.sh; do
    if ! bash "$DIR/$step"; then
        echo "Some tests failed."
        exit 1
    fi
done

echo "All tests passed."
