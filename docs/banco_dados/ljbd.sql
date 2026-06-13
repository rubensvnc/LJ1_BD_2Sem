CREATE DATABASE ljbd;
USE ljbd;

CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `login` varchar(50) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `perfil` enum('ADM','PROFISSIONAL','CLIENTE') NOT NULL,
  `tipo_profissional` enum('MERCADO','FARMACIA','SALAO') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cliente` (
  `id` int NOT NULL,
  `balanca` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `servico` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `duracao` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `agenda` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cliente_id` int NOT NULL,
  `profissional_id` int NOT NULL,
  `servico_id` int NOT NULL,
  `hora_agendado` time NOT NULL,
  `status_conclusao` enum('PENDENTE','EM ANDAMENTO','CONCLUIDO','CANCELADO') NOT NULL DEFAULT 'PENDENTE',
  `pago` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `cliente_id` (`cliente_id`),
  KEY `profissional_id` (`profissional_id`),
  KEY `servico_id` (`servico_id`),
  KEY `idx_agenda_pago` (`pago`),
  CONSTRAINT `agenda_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `agenda_ibfk_2` FOREIGN KEY (`profissional_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `agenda_ibfk_3` FOREIGN KEY (`servico_id`) REFERENCES `servico` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `produto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `codigo_barras` varchar(50) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `preco_base` decimal(10,2) NOT NULL,
  `validade` date DEFAULT NULL,
  `tipo` enum('MERCADO','FARMACIA') NOT NULL,
  `qtd` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_barras` (`codigo_barras`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `remedio` (
  `id_produto` int NOT NULL,
  `usa_receita` tinyint(1) NOT NULL DEFAULT '0',
  `crm_medico` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_produto`),
  CONSTRAINT `remedio_ibfk_1` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `venda` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cliente_id` int NOT NULL,
  `produto_id` int NOT NULL,
  `qtd` int NOT NULL,
  `preco_venda` decimal(10,2) NOT NULL,
  `data_venda` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `cliente_id` (`cliente_id`),
  KEY `produto_id` (`produto_id`),
  CONSTRAINT `venda_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `venda_ibfk_2` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;