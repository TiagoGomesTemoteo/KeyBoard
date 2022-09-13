describe('Cliente', () => {
  it('Cadastrar', () => {

    cy.visit('http://localhost:8080/KeyBoard/')
    
    cy.get('[href="form_cliente.jsp"]').click()
    
    cy.get('[name="nome"]')               .type('Tiago Gomes Temoteo')
    cy.get('[name="dtNascimento"]')       .type('16/06/2001')
    cy.get('[name="telefone"]')           .type('11985685471')
    cy.get('[name="genero"]')             .select('masculino')
    cy.get('[name="cpf"]')                .type('42346281824')
    cy.get('[type="email"]')              .type('tiago.gomes.temoteo@gmail.com')
    cy.get('[name="senha"]')              .type('123456')
    cy.get('[name="confirmar_senha"]')    .type('123456')
    
    cy.get('[name="cep"]')                .type('08597710')
    cy.get('[name="tipoResidencia"]')     .select('casa')
    cy.get('[name="numero"]')             .type('101')
    cy.get('[name="observacoes"]')        .type('Portão Laranja')
    cy.get('[name="tipoLogradouro"]')     .select('rua')
    cy.get('[name="logradouro"]')         .type('Adonias Filho')
    cy.get('[name="bairro"]')             .type('Jardim Moraes')
    cy.get('[name="cidade"]')             .type('Itaquaquecetuba')
    cy.get('[name="estado"]')             .type('São Paulo')
    cy.get('[name="pais"]')               .type('Brasil')
    cy.get('[name="identificacao"]')      .type('Minha casa')

    cy.get('[name="cartao_numero"]')       .type('12345')
    cy.get('[name="bandeira"]')            .select('VISA')
    cy.get('[name="nomeImpressoNoCartao"]').type('TIAGO G TEMOTEO')
    cy.get('[name="codSeguranca"]')        .type('568')

    cy.get('[name="operacao"]').click()
  }),

  it('Visualizar', () => {

    cy.visit('http://localhost:8080/KeyBoard/cliente?operacao=CONSULTAR')
    
    cy.get(':nth-child(2) > :nth-child(11) > a').click()
    
  }),

  it('Alterar', () => {

    cy.get('[name="nome"]')               .clear().type('Tiago Gomes')
    cy.get('[name="dtNascimento"]')       .clear().type('16/06/2001')
    cy.get('[name="telefone"]')           .clear().type('11985685471')
    cy.get('[name="genero"]')             .select('masculino')
    cy.get('[name="cpf"]')                .clear().type('13440272028')
    cy.get('[type="email"]')              .clear().type('tiago.gomes@gmail.com')
    cy.get('[name="senha"]')              .clear().type('123456')
    cy.get('[name="confirmar_senha"]')    .clear().type('123456')
    
    cy.get('[name="cep"]')                .clear().type('08597710')
    cy.get('[name="tipoResidencia"]')     .select('casa')
    cy.get('[name="numero"]')             .clear().type('110')
    cy.get('[name="observacoes"]')        .clear().type('Portão Azul')
    cy.get('[name="tipoLogradouro"]')     .select('rua')
    cy.get('[name="logradouro"]')         .clear().type('Adonias Filho')
    cy.get('[name="bairro"]')             .clear().type('Jardim Moraes')
    cy.get('[name="cidade"]')             .clear().type('Itaquaquecetuba')
    cy.get('[name="estado"]')             .clear().type('São Paulo')
    cy.get('[name="pais"]')               .clear().type('Brasil')
    cy.get('[name="identificacao"]')      .clear().type('Minha casa')

    cy.get('[name="cartao_numero"]')       .clear().type('12345')
    cy.get('[name="bandeira"]')            .select('VISA')
    cy.get('[name="nomeImpressoNoCartao"]').clear().type('TIAGO G TEMOTEO')
    cy.get('[name="codSeguranca"]')        .clear().type('568')
    
    cy.get('[name="operacao"]').click()
  }),

  it('Inativar/Ativar', () => {

    cy.visit('http://localhost:8080/KeyBoard/cliente?operacao=CONSULTAR')
    
    cy.get(':nth-child(2) > :nth-child(12)').click()
    
  }),

  it('Consultar', () => {

    cy.visit('http://localhost:8080/KeyBoard/cliente?operacao=CONSULTAR')
    
  })
})
