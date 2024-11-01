CREATE DATABASE InsightTrip;
USE InsightTrip;

create user 'API'@'localhost' identified by 'webDataViz0API';
grant insert, select, update on InsightTrip.* to 'API'@'localhost';
show grants for 'API'@'localhost';

CREATE TABLE Funcionario (
    idFuncionario INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(45),
    CPF CHAR(14),
    Email VARCHAR(256),
    Senha VARCHAR(45),
    Telefone VARCHAR(14),
    Setor VARCHAR(45),
    fkAdministrador INT,
    CONSTRAINT fkFuncionario FOREIGN KEY (fkAdministrador) 
        REFERENCES Funcionario(idFuncionario)
);

CREATE TABLE Agencia (
    idAgencia INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(45),
    CNPJ CHAR(14), 
    Endereco VARCHAR(45),
    CEP VARCHAR(8), 
    fkAdministrador INT,
    CONSTRAINT fkAdministradorAgencia FOREIGN KEY (fkAdministrador) 
        REFERENCES Funcionario(idFuncionario)
);

CREATE TABLE Pais (
    idPais INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(45),
    Continente VARCHAR(45)
);

CREATE TABLE UF ( 
    CodigoIBGE INT PRIMARY KEY,
    Nome VARCHAR(45),
    Regiao VARCHAR(45)
);

CREATE TABLE Criminalidade (
    idCriminalidade INT PRIMARY KEY AUTO_INCREMENT,
    DataHora DATETIME,
    QtdVitimas INT, 
    MunicipiosPerigosos VARCHAR(45),
    fkEstado INT,
    CONSTRAINT fkEstadoCriminalidade FOREIGN KEY (fkEstado) 
        REFERENCES UF(CodigoIBGE) 
);

CREATE TABLE Eventos (
    idEventos INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(45),
    Descricao VARCHAR(45)
);

CREATE TABLE EstadoHasEventos (
    idEventoEstado INT PRIMARY KEY AUTO_INCREMENT,
    dtEventoInicio DATE,
    dtEventoTermino DATE,
    fkEstado INT,
    CONSTRAINT fkEstadoEventos FOREIGN KEY (fkEstado) 
        REFERENCES UF(CodigoIBGE),
    fkEventos INT,
    CONSTRAINT fkEventosEstado FOREIGN KEY (fkEventos) 
        REFERENCES Eventos(idEventos)
);

CREATE TABLE Aeroporto (
    idAeroporto INT PRIMARY KEY AUTO_INCREMENT,
    NomeAeroporto VARCHAR(75),
    fkEstado INT,
    fkPais INT,
    CONSTRAINT fkEstadoAeroportos FOREIGN KEY (fkEstado) 
        REFERENCES UF(CodigoIBGE),
	CONSTRAINT fkPaisAeroportos FOREIGN KEY (fkPais) 
        REFERENCES Pais(idPais)
);

CREATE TABLE Viagem (
    idPassagem INT PRIMARY KEY AUTO_INCREMENT,
    dtViagem DATE,
    fkAeroportoOrigem INT, 
    fkAeroportoDestino INT,
    QtdPassageirosPagos INT,
    QtdPassageirosGratis INT,
    CONSTRAINT fkAeroportoOrigem FOREIGN KEY (fkAeroportoOrigem) 
        REFERENCES Aeroporto(idAeroporto),
    CONSTRAINT fkAeroportoDestino FOREIGN KEY (fkAeroportoDestino) 
        REFERENCES Aeroporto(idAeroporto)
);

select * from pais;
select * from UF;
select * from aeroporto;

SELECT idAeroporto, nomeAeroporto, UF.Nome AS NomeUF, Pais.Nome AS NomePais
FROM aeroporto
JOIN Pais ON idPais = fkPais
LEFT JOIN UF ON CodigoIBGE = fkEstado
ORDER BY Pais.Nome;

SELECT idPassagem, dtViagem AS 'Data', 
AeroportoOrigem.NomeAeroporto AS 'Aeroporto Origem', PaisOrigem.Nome AS 'País Origem', UFOrigem.Nome AS 'Estado Origem', 
AeroportoDestino.NomeAeroporto AS 'Aeroporto Destino', PaisDestino.Nome AS 'País Destino', UFDestino.Nome AS 'Estado Destino', 
QtdPassageirosPagos AS 'Passageiros Pagos', QtdPassageirosGratis AS 'Passageiros Grátis' FROM Viagem
JOIN Aeroporto AS AeroportoOrigem ON fkAeroportoOrigem = AeroportoOrigem.idAeroporto
JOIN Aeroporto AS AeroportoDestino ON fkAeroportoDestino = AeroportoDestino.idAeroporto
JOIN Pais AS PaisOrigem ON AeroportoOrigem.fkPais = PaisOrigem.IdPais
JOIN Pais AS PaisDestino ON AeroportoDestino.fkPais = PaisDestino.IdPais
LEFT JOIN UF AS UFOrigem ON AeroportoOrigem.fkEstado = UFOrigem.CodigoIBGE
JOIN UF AS UFDestino ON AeroportoDestino.fkEstado = UFDestino.CodigoIBGE
ORDER BY dtViagem LIMIT 100000;


-- DROP USER 'API'@'localhost'; --
-- DROP DATABASE InsightTrip--