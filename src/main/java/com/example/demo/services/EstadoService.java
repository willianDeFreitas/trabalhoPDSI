package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Estado;
import com.example.demo.repositories.EstadoRepository;
import com.example.demo.services.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;
	
	public Estado buscar(Integer id) {
		Estado obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", Tipo: "+ Estado.class.getName());
		}
		return obj;
	}
	public void inserir(Estado obj) {
		obj.setId(null);
		repo.save(obj);
	}
}
