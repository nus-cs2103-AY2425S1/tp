#!/bin/bash

# Loop through all files in the hooks directory and make them executable
for hook in hooks/*; do
    if [ -f "$hook" ]; then
        chmod +x "$hook"
        echo "Made $hook executable."
    fi
done

echo "Setup completed: All hooks are now executable."
