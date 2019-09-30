#!/bin/sh
make -C /Users/sunjm/Downloads/xlnt-master/source -f /Users/sunjm/Downloads/xlnt-master/source/CMakeScripts/xlnt_postBuildPhase.make$CONFIGURATION OBJDIR=$(basename "$OBJECT_FILE_DIR_normal") all
