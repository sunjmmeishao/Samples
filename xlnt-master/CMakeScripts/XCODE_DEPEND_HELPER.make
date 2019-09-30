# DO NOT EDIT
# This makefile makes sure all linkable targets are
# up-to-date with anything they link to
default:
	echo "Do not invoke directly"

# Rules to remove targets that are older than anything to which they
# link.  This forces Xcode to relink the targets from scratch.  It
# does not seem to check these dependencies itself.
PostBuild.libstudxml.Debug:
PostBuild.xlnt.Debug:
PostBuild.libstudxml.Debug: /Users/sunjm/Downloads/xlnt-master/source/Debug/libxlntd.dylib
/Users/sunjm/Downloads/xlnt-master/source/Debug/libxlntd.dylib:\
	/Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/xlnt_all.build/Debug/libstudxml.build/liblibstudxml.a
	/bin/rm -f /Users/sunjm/Downloads/xlnt-master/source/Debug/libxlntd.dylib


PostBuild.xlnt.test.Debug:
PostBuild.xlnt.Debug: /Users/sunjm/Downloads/xlnt-master/tests/Debug/xlnt.test
PostBuild.libstudxml.Debug: /Users/sunjm/Downloads/xlnt-master/tests/Debug/xlnt.test
/Users/sunjm/Downloads/xlnt-master/tests/Debug/xlnt.test:\
	/Users/sunjm/Downloads/xlnt-master/source/Debug/libxlntd.1.2.dylib\
	/Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/xlnt_all.build/Debug/libstudxml.build/liblibstudxml.a
	/bin/rm -f /Users/sunjm/Downloads/xlnt-master/tests/Debug/xlnt.test


PostBuild.libstudxml.Release:
PostBuild.xlnt.Release:
PostBuild.libstudxml.Release: /Users/sunjm/Downloads/xlnt-master/source/Release/libxlnt.dylib
/Users/sunjm/Downloads/xlnt-master/source/Release/libxlnt.dylib:\
	/Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/xlnt_all.build/Release/libstudxml.build/liblibstudxml.a
	/bin/rm -f /Users/sunjm/Downloads/xlnt-master/source/Release/libxlnt.dylib


PostBuild.xlnt.test.Release:
PostBuild.xlnt.Release: /Users/sunjm/Downloads/xlnt-master/tests/Release/xlnt.test
PostBuild.libstudxml.Release: /Users/sunjm/Downloads/xlnt-master/tests/Release/xlnt.test
/Users/sunjm/Downloads/xlnt-master/tests/Release/xlnt.test:\
	/Users/sunjm/Downloads/xlnt-master/source/Release/libxlnt.1.2.dylib\
	/Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/xlnt_all.build/Release/libstudxml.build/liblibstudxml.a
	/bin/rm -f /Users/sunjm/Downloads/xlnt-master/tests/Release/xlnt.test


PostBuild.libstudxml.MinSizeRel:
PostBuild.xlnt.MinSizeRel:
PostBuild.libstudxml.MinSizeRel: /Users/sunjm/Downloads/xlnt-master/source/MinSizeRel/libxlnt.dylib
/Users/sunjm/Downloads/xlnt-master/source/MinSizeRel/libxlnt.dylib:\
	/Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/xlnt_all.build/MinSizeRel/libstudxml.build/liblibstudxml.a
	/bin/rm -f /Users/sunjm/Downloads/xlnt-master/source/MinSizeRel/libxlnt.dylib


PostBuild.xlnt.test.MinSizeRel:
PostBuild.xlnt.MinSizeRel: /Users/sunjm/Downloads/xlnt-master/tests/MinSizeRel/xlnt.test
PostBuild.libstudxml.MinSizeRel: /Users/sunjm/Downloads/xlnt-master/tests/MinSizeRel/xlnt.test
/Users/sunjm/Downloads/xlnt-master/tests/MinSizeRel/xlnt.test:\
	/Users/sunjm/Downloads/xlnt-master/source/MinSizeRel/libxlnt.1.2.dylib\
	/Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/xlnt_all.build/MinSizeRel/libstudxml.build/liblibstudxml.a
	/bin/rm -f /Users/sunjm/Downloads/xlnt-master/tests/MinSizeRel/xlnt.test


PostBuild.libstudxml.RelWithDebInfo:
PostBuild.xlnt.RelWithDebInfo:
PostBuild.libstudxml.RelWithDebInfo: /Users/sunjm/Downloads/xlnt-master/source/RelWithDebInfo/libxlnt.dylib
/Users/sunjm/Downloads/xlnt-master/source/RelWithDebInfo/libxlnt.dylib:\
	/Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/xlnt_all.build/RelWithDebInfo/libstudxml.build/liblibstudxml.a
	/bin/rm -f /Users/sunjm/Downloads/xlnt-master/source/RelWithDebInfo/libxlnt.dylib


PostBuild.xlnt.test.RelWithDebInfo:
PostBuild.xlnt.RelWithDebInfo: /Users/sunjm/Downloads/xlnt-master/tests/RelWithDebInfo/xlnt.test
PostBuild.libstudxml.RelWithDebInfo: /Users/sunjm/Downloads/xlnt-master/tests/RelWithDebInfo/xlnt.test
/Users/sunjm/Downloads/xlnt-master/tests/RelWithDebInfo/xlnt.test:\
	/Users/sunjm/Downloads/xlnt-master/source/RelWithDebInfo/libxlnt.1.2.dylib\
	/Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/xlnt_all.build/RelWithDebInfo/libstudxml.build/liblibstudxml.a
	/bin/rm -f /Users/sunjm/Downloads/xlnt-master/tests/RelWithDebInfo/xlnt.test




# For each target create a dummy ruleso the target does not have to exist
/Users/sunjm/Downloads/xlnt-master/source/Debug/libxlntd.1.2.dylib:
/Users/sunjm/Downloads/xlnt-master/source/MinSizeRel/libxlnt.1.2.dylib:
/Users/sunjm/Downloads/xlnt-master/source/RelWithDebInfo/libxlnt.1.2.dylib:
/Users/sunjm/Downloads/xlnt-master/source/Release/libxlnt.1.2.dylib:
/Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/xlnt_all.build/Debug/libstudxml.build/liblibstudxml.a:
/Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/xlnt_all.build/MinSizeRel/libstudxml.build/liblibstudxml.a:
/Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/xlnt_all.build/RelWithDebInfo/libstudxml.build/liblibstudxml.a:
/Users/sunjm/Downloads/xlnt-master/source/third-party/libstudxml/xlnt_all.build/Release/libstudxml.build/liblibstudxml.a:
