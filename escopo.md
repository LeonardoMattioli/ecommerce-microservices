Responsabilidade e Comunicação de Cada Serviço

Aqui está o detalhamento de cada peça do quebra-cabeça, sua única responsabilidade e como ela se comunica com as outras.

Serviços de Infraestrutura

API Gateway (gateway)

Responsabilidade: É a única porta de entrada do sistema. Funciona como um segurança e um recepcionista.

O que ele faz:

Recebe todas as requisições externas.

Valida o token de autenticação (JWT) para saber se o usuário está logado.

Aplica regras de segurança, como limitar a quantidade de requisições (Rate Limiting).

Roteia cada requisição para o microserviço interno correto (ex: /products vai para catalog-service) .

Comunicação: Recebe chamadas REST do mundo externo e as encaminha como chamadas REST para os serviços internos.

Service Discovery (service-discovery / Eureka)



Responsabilidade: Funciona como uma "lista telefônica" ou "agenda de contatos" para todos os outros serviços.

O que ele faz:

Mantém um registro atualizado do endereço (IP e porta) de cada microserviço que está online.

Comunicação: Os microerviços se "registram" no Eureka quando iniciam e enviam um sinal de vida ("heartbeat") periodicamente. O API Gateway e outros serviços o consultam para encontrar o endereço dos serviços com quem precisam falar.

Config Server (config-server)



Responsabilidade: Centralizar e gerenciar os arquivos de configuração de todos os serviços.

O que ele faz:

Guarda as configurações (como senhas de banco de dados, chaves de API, etc.) em um único local.

Comunicação: Quando um microserviço inicia, ele primeiro se comunica com o Config Server para buscar suas próprias configurações antes de continuar.

Serviços de Domínio (Negócio)

Users Service (users-service)



Responsabilidade: Gerenciar a identidade, o cadastro e a autenticação dos usuários.

O que ele faz:

Cadastra novos usuários e armazena suas senhas de forma segura (usando BCrypt).

Valida as credenciais no login e emite os tokens de acesso (JWT).

Comunicação: Recebe chamadas REST do API Gateway. Ele é um serviço final, não se comunica ativamente com outros serviços de negócio.

Catalog Service (catalog-service)



Responsabilidade: Ser a fonte da verdade para todas as informações de produtos e categorias.

O que ele faz:

Permite criar, ler, atualizar e deletar produtos (CRUD).

Oferece funcionalidades de busca e paginação.

Comunicação: Recebe chamadas REST do API Gateway para exibir produtos ao usuário. Na fase síncrona, também recebe chamadas REST do orders-service para validação de itens.

Orders Service (orders-service)



Responsabilidade: Gerenciar o ciclo de vida completo de um pedido, desde a sua criação até a finalização.

O que ele faz:

Cria um pedido com o status inicial CREATED.

Gerencia as mudanças de estado do pedido (PAID, CANCELLED, etc.).

Na fase assíncrona, ele é o iniciador da saga de checkout, publicando o evento order.created. Ele também consome eventos de resultado para atualizar o status final do pedido.

Comunicação: Recebe chamadas REST do Gateway para criar e consultar pedidos. Se comunica com outros serviços publicando e consumindo Eventos via Kafka.

Inventory Service (inventory-service)



Responsabilidade: Controlar o estoque de cada produto (SKU) de forma atômica e confiável.

O que ele faz:

Reserva itens do estoque quando um pedido é criado.

Confirma a reserva (dando baixa no estoque) quando o pagamento é aprovado.

Libera os itens (rollback) se o pedido for cancelado ou o pagamento falhar.

Comunicação: Sua principal forma de comunicação é assíncrona. Ele consome Eventos do Kafka (como order.created) e publica outros Eventos (como stock.reserved).

Payment Service (payment-service - Mock)



Responsabilidade: Simular a interação com um provedor de pagamento externo.

O que ele faz:

Simula a aprovação ou negação de um pagamento, com um atraso para imitar a realidade.

Comunicação: Na fase síncrona, recebe chamadas REST do orders-service. Na fase assíncrona, ele consome um Evento do Kafka (stock.reserved) e publica o resultado em outros Eventos (payment.approved / payment.declined).

Notification Service (notification-service)



Responsabilidade: Enviar notificações (simuladas, como e-mails) ao usuário.

O que ele faz:

Envia uma notificação de "pedido confirmado" ou "pedido cancelado".

Comunicação: É um serviço puramente reativo. Ele apenas consome Eventos do Kafka (order.paid, order.cancelled) e não se comunica com mais ninguém.





1. users-service

(Responsável pela identidade e autenticação)

Este serviço possui duas tabelas principais para gerenciar os usuários e seus tokens de acesso.





Tabela users

id UUID PK: É o identificador único universal do usuário. Usamos UUID em vez de um número sequencial (1, 2, 3) porque em sistemas distribuídos é mais seguro e evita colisões. PK significa Chave Primária.

name VARCHAR: O nome do usuário.

email VARCHAR unique: O e-mail usado para login. A restrição unique garante que não existam dois usuários com o mesmo e-mail.

password_hash VARCHAR: Importante: Nunca guardamos a senha real do usuário. Este campo armazena uma versão criptografada da senha (um "hash"), gerada por um algoritmo seguro como o BCrypt. Quando o usuário tenta logar, criptografamos a senha que ele digitou e comparamos o resultado com o que está guardado aqui.

role ENUM('USER','ADMIN'): Define o nível de permissão do usuário. ENUM é um tipo que só aceita valores pré-definidos (USER ou ADMIN), garantindo a consistência dos papéis.

created_at TIMESTAMP: Um registro de data e hora de quando o usuário foi criado, útil para auditoria.



Tabela refresh_tokens

id UUID PK: O identificador único do refresh token.

user_id FK: A chave estrangeira (FK) que liga este token a um usuário específico na tabela users.

token_hash VARCHAR: Assim como a senha, não guardamos o refresh token original, mas sim uma versão criptografada dele por segurança.

revoked BOOLEAN: Um campo booleano (true/false) para invalidar um token. Se um token for suspeito de roubo, podemos marcá-lo como revoked para que não possa mais ser usado.

expires_at TIMESTAMP: A data e hora em que este token irá expirar e não poderá mais ser usado para gerar novos tokens de acesso.