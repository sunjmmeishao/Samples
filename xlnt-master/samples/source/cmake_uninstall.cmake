if(NOT EXISTS "/Users/sunjm/Downloads/xlnt-master/samples/install_manifest.txt")
  message(FATAL_ERROR "Cannot find install manifest: /Users/sunjm/Downloads/xlnt-master/samples/install_manifest.txt")
endif(NOT EXISTS "/Users/sunjm/Downloads/xlnt-master/samples/install_manifest.txt")

file(READ "/Users/sunjm/Downloads/xlnt-master/samples/install_manifest.txt" files)
string(REGEX REPLACE "\n" ";" files "${files}")
foreach(file ${files})
  message(STATUS "Uninstalling $ENV{DESTDIR}${file}")
  if(IS_SYMLINK "$ENV{DESTDIR}${file}" OR EXISTS "$ENV{DESTDIR}${file}")
    exec_program(
      "/Applications/CMake.app/Contents/bin/cmake" ARGS "-E remove \"$ENV{DESTDIR}${file}\""
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

message(STATUS "Uninstalling /usr/local/include/xlnt")

exec_program("/Applications/CMake.app/Contents/bin/cmake" 
              ARGS "-E remove_directory /usr/local/include/xlnt"
              OUTPUT_VARIABLE rm_out
              RETURN_VALUE rm_retval
)

if(NOT "${rm_retval}" STREQUAL 0)
    message(FATAL_ERROR "Problem when removing /usr/local/include/xlnt")
endif()
