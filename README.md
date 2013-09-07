# RTPhone

## Descrição
Construir o protótipo de um sistema de comunicação de voz sobre a internet utilizando RTP a partir de computadores PC. O sistema consiste de dois módulos software: um módulo cliente e um módulo servidor. A comunicação permitida é apenas um-a-um. O servidor gerencia apenas 10 clientes previamente cadastrados. Funcionamento: Quando um cliente é iniciado em um computador, cadastra-se no servidor para informar que está ativo e o endereço {porta, ip} no qual pode ser encontrado. Periodicamente, o cliente envia mensagem ao servidor indicando que está ativo. O servidor muda o status de um cliente para inativo se não recebe uma mensagem
após um tempo predeterminado. Quando o ususário1 deseja falar com outro usuário2, ele informa o endereço de e-mail do usuário2 ao cliente e este consulta o servidor para saber se usuário2 está ativo e qual o seu endereço {porta, ip}. A partir daí, se usuário2 está ativo, a comunicação é diretamente entre usuário1 e usuário2, utilizando RTP.


## TODO
* Integração dos codigos (Judah Holanda)
* Integração dos interface (Kairo Tavares)
* Protocolo pts (Phone to Server) (David Araujo)
* Testes

## Protocolo pts (Phone to Server)
* XML Like
 
    * <<DADO>> STRING <<DADO>>

            e.g. <USERNAME> DAVID <USERNAME>