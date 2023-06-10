FROM mysql:latest
# Adicionando os scripts SQL para serem executados na criação do banco
COPY ./config/database/ /docker-entrypoint-initdb.d/