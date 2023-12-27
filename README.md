# Consumindo API em Java
Este projeto foi feito em Java com Spring Boot e realiza consultas à API da Tabela FIPE (https://deividfortuna.github.io/fipe/) para obter informações sobre veículos.

## Sobre o projeto
Algumas das principais funcionalidades implementadas neste projeto:
- É possível pesquisar por tipo de veículo (carro, moto ou caminhão).
- É possível filtrar veículos por marca, nome e visualizar informações gerais como valor, marca, modelo, ano do modelo e tipo de combustível.
- A aplicação possui um Regex para acentuação, eliminando tal erro para o usuário.
- A classe Menu foi refatorada utilizando o princípio da responsabilidade única através dos métodos principais.
- A aplicação converte os dados recebidos da API utilizando Object Mapper e Generics.
- O uso de streams também está presente, inclusive utilizando o método "filter" para alterar a ordem dos veículos com seus códigos em numeração crescente.

## Configuração
1. **Requisitos**
- Certifique-se de ter o Java instalado em sua máquina.
2. **Clone o repositório**
```bash
git clone https://github.com/camilazucchi/consulta-fipe.git
cd consultafipe
