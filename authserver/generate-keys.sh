#!/usr/bin/env bash

openssl genpkey -algorithm RSA -out ./src/main/resources/certs/private_key.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -in ./src/main/resources/certs/private_key.pem -out ./src/main/resources/certs/private_key_pkcs8.pem -nocrypt
openssl rsa -pubout -in ./src/main/resources/certs/private_key_pkcs8.pem -out ./src/main/resources/certs/public_key.pem
mv ./src/main/resources/certs/private_key.pem ./src/main/resources/certs/app.key
mv ./src/main/resources/certs/public_key.pem ./src/main/resources/certs/app.pub

rm ./src/main/resources/certs/private_key_pkcs8.pem
# openssl req -new -x509 -key private_key_pkcs8.pem -out cert.pem -days 365