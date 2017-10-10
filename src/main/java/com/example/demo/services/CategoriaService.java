package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Categoria;
import com.example.demo.repositories.CategoriaRepository;
import com.example.demo.services.exceptions.DataIntegrityException;
import com.example.demo.services.exceptions.ObjectNotFoundException;

import dto.CategoriaDTO;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", Tipo: "+ Categoria.class.getName());
		}
		return obj;
	}
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		//prepara consulta para retorno de dados
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		//sobrecarga de metodos e retorno de pagina
		return repo.findAll(pageRequest);
	}
	//metodo auxiliar que instacia uma categoria a partir de um DTO
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
}
