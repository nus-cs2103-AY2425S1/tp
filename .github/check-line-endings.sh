#!/bin/sh
# Checks for prohibited line endings.
# Prohibited line endings: \r\n
# Exclude .vcf files as the RFC states that they must use \r\n

git grep --cached -I -n --no-color -P '\r$' -- ':(exclude)*.vcf' ':/' |
awk '
    BEGIN {
        FS = ":"
        OFS = ":"
        ret = 0
    }
    {
        ret = 1
        print "ERROR", $1, $2, " prohibited \\r\\n line ending, use \\n instead."
    }
    END {
        exit ret
    }
'
