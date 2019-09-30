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
PostBuild.libstudxml.Debug: /Users/sunjm/Downloads/xlnt-master/samples/source/Debug/libxlntd.dylib
/Users/sunjm/Downloads/xlnt-master/samples/source/Debug/libxlntd.dylib:\
	/Users/sunjm/Downloads/xlnt-master/samples/source/third-party/libstudxml/xlnt.build/Debug/libstudxml.build/liblibstudxml.a
	/bin/rm -f /Users/sunjm/Downloads/xlnt-master/samples/source/Debug/libxlntd.dylib


PostBuild.libstudxml.Release:
PostBuild.xlnt.Release:
PostBuild.libstudxml.Release: /Users/sunjm/Downloads/xlnt-master/samples/source/Release/libxlnt.dylib
/Users/sunjm/Downloads/xlnt-master/samples/source/Release/libxlnt.dylib:\
	/Users/sunjm/Downloads/xlnt-master/samples/source/third-party/libstudxml/xlnt.build/Release/libstudxml.build/liblibstudxml.a
	/bin/rm -f /Users/sunjm/Downloads/xlnt-master/samples/source/Release/libxlnt.dylib


PostBuild.libstudxml.MinSizeRel:
PostBuild.xlnt.MinSizeRel:
PostBuild.libstudxml.MinSizeRel: /Users/sunjm/Downloads/xlnt-master/samples/source/MinSizeRel/libxlnt.dylib
/Users/sunjm/Downloads/xlnt-master/samples/source/MinSizeRel/libxlnt.dylib:\
	/Users/sunjm/Downloads/xlnt-master/samples/source/third-party/libstudxml/xlnt.build/MinSizeRel/libstudxml.build/liblibstudxml.a
	/bin/rm -f /Users/sunjm/Downloads/xlnt-master/samples/source/MinSizeRel/libxlnt.dylib


PostBuild.libstudxml.RelWithDebInfo:
PostBuild.xlnt.RelWithDebInfo:
PostBuild.libstudxml.RelWithDebInfo: /Users/sunjm/Downloads/xlnt-master/samples/source/RelWithDebInfo/libxlnt.dylib
/Users/sunjm/Downloads/xlnt-master/samples/source/RelWithDebInfo/libxlnt.dylib:\
	/Users/sunjm/Downloads/xlnt-master/samples/source/third-party/libstudxml/xlnt.build/RelWithDebInfo/libstudxml.build/liblibstudxml.a
	/bin/rm -f /Users/sunjm/Downloads/xlnt-master/samples/source/RelWithDebInfo/libxlnt.dylib




# For each target create a dummy ruleso the target does not have to exist
/Users/sunjm/Downloads/xlnt-master/samples/source/third-party/libstudxml/xlnt.build/Debug/libstudxml.build/liblibstudxml.a:
/Users/sunjm/Downloads/xlnt-master/samples/source/third-party/libstudxml/xlnt.build/MinSizeRel/libstudxml.build/liblibstudxml.a:
/Users/sunjm/Downloads/xlnt-master/samples/source/third-party/libstudxml/xlnt.build/RelWithDebInfo/libstudxml.build/liblibstudxml.a:
/Users/sunjm/Downloads/xlnt-master/samples/source/third-party/libstudxml/xlnt.build/Release/libstudxml.build/liblibstudxml.a:
