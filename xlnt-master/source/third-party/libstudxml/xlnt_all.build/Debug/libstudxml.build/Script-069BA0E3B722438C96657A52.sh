#!/bin/sh
make -C /Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml -f /Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/CMakeScripts/libstudxml_postBuildPhase.make$CONFIGURATION OBJDIR=$(basename "$OBJECT_FILE_DIR_normal") all
