#!/bin/sh

# Copy pre-commit hook to the .git/hooks directory
cp hooks/pre-commit .git/hooks/pre-commit

# Make the pre-commit hook executable
chmod +x .git/hooks/pre-commit

echo "Git hooks installed."
