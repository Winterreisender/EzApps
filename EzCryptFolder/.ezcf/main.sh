#/bin/bash
export LESSHARESET=utf-8
BIN_DIR=$(cd $(dirname $0);pwd)
BASE_DIR=$(dirname $BIN_DIR)
DATA_FILE="${BIN_DIR}/cfdata.7z"

alias 7za="${BIN_DIR}/7za"
alias zenity="${BIN_DIR}/zenity"

echo BASE_DIR=${BASE_DIR}

password=$(zenity --password)

function encrypt_folder() {
    7z a "-p${password}" -r -xr!cfdata -mhe=on -mx=0 $DATA_FILE $BASE_DIR/* -xr"!.ezcf/" -sdel
}

function decrypt_folder() {
    7z x -y "-p${password}"  "-o${BASE_DIR}" $DATA_FILE -xr"!.ezcf/" && rm $DATA_FILE
}

if [ -f ${DATA_FILE} ]; then
    decrypt_folder
    zenity --info --text "Decrypted. Press OK to re-encrypt."
fi

encrypt_folder
zenity --info --text "Encrypted"
