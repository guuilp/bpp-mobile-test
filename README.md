# bpp-mobile-test

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/93e40e4f9e264a12b329e42ba976d96c)](https://app.codacy.com/app/guuilp/bpp-mobile-test?utm_source=github.com&utm_medium=referral&utm_content=guuilp/bpp-mobile-test&utm_campaign=badger)



>### OBSERVAÇÕES
>
>* Realize um fork deste projeto para subir seu desafio.
>* Com base nos requisitos e documentação fornecida utilize seu conhecimento para construir um app que atenda os critérios propostos. 
>* Você possui total liberdade para definir os requisitos não funcionais, arquitetura e os padrões técnicos que irá utilizar no desenvolvimento do aplicativo.  
>* Forma e função caminham juntos na concepção de um app mobile e por isso você terá total liberdade para prototipar e desenvolver o visual e a experiência deste aplicativo.
>* Não esqueça de desenvolver os testes unitários. 





>### REQUISITOS
>
>**LOGIN**
>
>Eu como usuário devo abrir meu aplicativo e visualizar a tela de login para que eu possa realizar minha autênticação informando email e senha e assim conseguir acessar a área privada. Caso eu não tenha permissões para acessar o aplicativo devo visualizar uma mensagem retornada pelo servidor. Caso eu possua credenciais para acessar o aplicativo devo ser direcionado para a tela timeline.
>
>**TIMELINE**
>
>Eu como usuário devo visualizar a timeline assim que minhas credenciais forem validadas podendo ver os gastos que realizei no período. Para exibição das informações é importante que eu consiga saber data, hora, local e status da transação realizada. 



Nós disponibilizamos uma simples API mockada para te ajudar no desenvolvimento do app. Está API possui as seguintes caraceterísticas:



>## LOGIN
>O serviço de login consiste em validar se um determinado usuário é um usuário válido, autorizando que o mesmo adentre a sessão privada do aplicativo. 
>
>#### REQUEST [POST]
>
>URL: http://test-mobile.dev-bpp.com.br/login
>
>##### PARÂMETROS
>
>* **email**: representa o email do usuário que está tentando acessar o aplicativo. [string]
>*  **password**: representa a senha do usuário que está tentando acessaro aplicativo. A senha deve ser fornecida após a conversão para o padrão base64. [string] 
>
>#### RESPONSE [JSON]
>
>* **status**: representa o status da validação do usuário. (success|error)[string]
>* **message**: este campo é retornado nos cenários de erro e representa o motivo pelo qual o erro ocorreu. [string]
>* **code**: representa o códigoda validação do usuário. (success:200|error:300)[int]
>
>**SUCCESS**
>~~~json
>{"status":"success","code":"200"}
>~~~
>
>**ERROR**
>~~~json
>{"status":"error","message":"usuario invalido","code":"300"}
>~~~
>
>**Atenção**: estes serviços são mockados e foram construidos apenas >para a execução do teste aqui mencionado. Para que o serviço de login retorne sucesso você deve realizar a requisição com os dados abaixo:
>~~~json
>    "email":"waldisney@brasilprepagos.com.br"
>    "password":"Br@silPP123"
>~~~






>## INVOICE
>
>O serviço de invoice consiste em retornar um conjunto de informações que representam os gastos de um determinado usuário em um período mockado. Como se trata de mock o servidor irá retornar sempre o mesmo modelo de dados independente se o usuário realizou ou não o login. Estas informações devem fazer parte da area privada do usuário onde o mesmo deverá acessar após passar pelo serviço de login. 
>
>OBS: Estes dados são dados fakes construidos apenas para composição deste teste. 
>
>#### REQUEST [GET]
>
>URL: http://test-mobile.dev-bpp.com.br/invoice
>
>##### PARÂMETROS
>
>N/A
>
>#### RESPONSE [JSON]
>
>* **transactionId**: representa o id da transação financeira.[string]
>* **transactionDate**: representa a data em que a transação foi efetivada.[string]
>* **transactionFormattedDate**: representa a data formatada em que a transação foi efetivada.[string]
>* **transactionCurrency**: representa o tipo de moeda utilizada na transação(BRL).[string]
>* **transactionAmount**: representa o valor da transação.[double]
>* **billingCurrency**: representa o tipo da moeda utilizada na cobrança(BRL).[string]
>* **billingAmount**: representa o valor da cobrança.[string]
>* **transactionStatus**: representa o status da transação:
>>* **Settled**: quando uma compra foi aprovada pelo sistema e estabelecida. 
>>* **Pending**: quando a compra está na fila de processamento e ainda não foi efetivada.
>>* **Reversed**: quando a compra foi cancelada pelo cliente manualmente.
>>* **Declined**: quando a compra foi recusada pelo sistema de processamento. 
>* **transactionName**: representa o nome técnico da transação. [string]
>* **merchantName**: representa o nome do estabelecimento onde a compra foi efetuada. [string]
>* **"mccCode"**: representa o código do tipo de estabelecimento. [string]
>* **"mccDescription"**: representa a descrição do tipo do estabelecimento. [int]


>* **status**: representa o status da validação do usuário. (success|error)[string]
>* **message**: este campo é retornado nos cenários de erro e representa o motivo pelo qual o erro ocorreu. [string]
>* **code**: representa o códigoda validação do usuário. (success:200|error:300)[int]
>
>**SUCCESS**
>~~~json
>[{ "transactionId": "800316627406TFI02AED464624XXXXXX1812",
>    "transactionDate": "03012018 13:18:13",
>    "transactionFormattedDate": "2018-01-03T13:18:13",
>    "transactionCurrency": "BRL",
>    "transactionAmount": 18.5,
>    "billingCurrency": "BRL",
>    "billingAmount": 18.5,
>    "transactionStatus": "Settled",
>    "transactionName": "000000-Purchase",
>    "merchantName": "PANIFICADORA BARAO ITA",
>    "mccCode": "5499",
>    "mccDescription": "Convenience / Delicatessens"}]
>~~~
>
>**ERROR**
>~~~json
>N/A
>~~~
>
>**Atenção**: estes serviços são mockados e foram construidos apenas para a execução do teste aqui mencionado.
