#/bin/bash
export LESSHARESET=utf-8
BIN_DIR=$(cd $(dirname $0);pwd)
BASE_DIR=$(dirname $BIN_DIR)
DATA_FILE="${BIN_DIR}/cfdata.7z"

alias 7za="${BIN_DIR}/7za"
alias zenity="${BIN_DIR}/zenity"


echo BASE_DIR=${BASE_DIR}

password=$(zenity --password)

if [ -f ${DATA_FILE} ]; then
    7z x -y "-p${password}"  "-o${BASE_DIR}" $DATA_FILE -xr"!.ezcf/"
    rm $DATA_FILE
else
    7z a "-p${password}" -r -xr!cfdata -mhe=on -mx=0 $DATA_FILE $BASE_DIR/* -xr"!.ezcf/" -sdel
fi