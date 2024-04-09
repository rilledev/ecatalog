package com.rx.ecatalog.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rx.ecatalog.dto.ProductDTO;
import com.rx.ecatalog.model.Product;
import com.rx.ecatalog.repositories.ProductRepository;
import com.rx.ecatalog.service.exceptions.DatabaseException;
import com.rx.ecatalog.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;



@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		Page<Product> list = repository.findAll(pageRequest);
		return list.map(x -> new ProductDTO(x));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());
	}
	
	@Transactional(readOnly = true)
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		//entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
		Product entity = repository.getReferenceById(id);
		//entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ProductDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id); 
		}
	}
    
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}
}
