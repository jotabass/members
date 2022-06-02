package br.com.jotabelk.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity // anotations do hibernate e jpa que indica que esta classe é uma tabela sem
		// esta anotation não funciona
@Table(name = "MEMBRO") // nome da tabela do banco de dados esta anotation é opcional
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Membro {
	@Id // mapear dizendo que é chave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) // mapeamento automatico do campo incremento, o banco de dados
														// gera o ID
	@Column(name = "IDMEMBRO") // mapeia as informacoes do campo
	private Integer idMembro;

	@Column(name = "NOME", length = 150, nullable = false)
	private String nome;

	@Column(name = "CPF", length = 15, nullable = false, unique = true)
	private String cpf;

	@Column(name = "EMAIL", length = 150, nullable = false, unique = true)
	private String email;

}
