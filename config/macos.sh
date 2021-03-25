
function configure_environment() {
    set_env_variables
}

function set_env_variables() {
    echo "Setting environment variables for Spring Api"

    shellProfileFile="";

    if [ "$SHELL" == "/bin/zsh" ]
    then
        shellProfileFile="~/.zprofile"
    fi 

    if [ "$SHELL" == "/bin/bash" ]
    then
        shellProfileFile="~/.bash_profile"
    fi

    if [ "$SHELL" == "/bin/sh" ]
    then
        shellProfileFile="~/.profile"
    fi

    

    if [[ -z $shellProfileFile ]]
    then
        echo "Undefined type of shell >>$SHELL<< used on this computer. There could be problems with developing api. Exit"
        exit 1
    fi

    update_or_append_variables $shellProfileFile
}

function update_or_append_variables {
    echo "Checking if profile file exists for '$1' ";

    path=$1;

    test -f $path || touch $path

    if [[ ! -f "$path" ]]; then
        echo "Profile file was not created"
    else
        echo "Profile file was created"
    fi
}

configure_environment