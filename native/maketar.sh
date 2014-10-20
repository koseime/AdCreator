function tar_dir_contents ()
{
    local DIRPATH="$1"
    local TARARCH="$2.tar.gz"
    local ORGIFS="$IFS"
    IFS=$'\n'
    tar -C "$DIRPATH" -czf "$TARARCH" $( ls -a "$DIRPATH" | grep -v '\(^\.$\)\|\(^\.\.$\)' )
    IFS="$ORGIFS"
}
