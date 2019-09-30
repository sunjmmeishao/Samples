if(NOT EXISTS "G:/Downloads/xlnt-master/vs/install_manifest.txt")
  message(FATAL_ERROR "Cannot find install manifest: G:/Downloads/xlnt-master/vs/install_manifest.txt")
endif(NOT EXISTS "G:/Downloads/xlnt-master/vs/install_manifest.txt")

file(READ "G:/Downloads/xlnt-master/vs/install_manifest.txt" files)
string(REGEX REPLACE "\n" ";" files "${files}")
foreach(file ${files})
  message(STATUS "Uninstalling $ENV{DESTDIR}${file}")
  if(IS_SYMLINK "$ENV{DESTDIR}${file}" OR EXISTS "$ENV{DESTDIR}${file}")
    exec_program(
      "C:/Users/Administrator/Desktop/cmake-3.15.3-win32-x86/bin/cmake.exe" ARGS "-E remove \"$ENV{DESTDIR}${file}\""
      OUTPUT_VARIABLE rm_out
      RETURN_VALUE rm_retval
      )
    if(NOT "${rm_retval}" STREQUAL 0)
      message(FATAL_ERROR "Problem when removing $ENV{DESTDIR}${file}")
    endif(NOT "${rm_retval}" STREQUAL 0)
  else(IS_SYMLINK "$ENV{DESTDIR}${file}" OR EXISTS "$ENV{DESTDIR}${file}")
    message(STATUS "File $ENV{DESTDIR}${file} does not exist.")
  endif(IS_SYMLINK "$ENV{DESTDIR}${file}" OR EXISTS "$ENV{DESTDIR}${file}")
endforeach(file)

message(STATUS "Uninstalling G:/Downloads/xlnt-master/vs/installed/include/xlnt")

exec_program("C:/Users/Administrator/Desktop/cmake-3.15.3-win32-x86/bin/cmake.exe" 
              ARGS "-E remove_directory G:/Downloads/xlnt-master/vs/installed/include/xlnt"
              OUTPUT_VARIABLE rm_out
              RETURN_VALUE rm_retval
)

if(NOT "${rm_retval}" STREQUAL 0)
    message(FATAL_ERROR "Problem when removing G:/Downloads/xlnt-master/vs/installed/include/xlnt")
endif()
