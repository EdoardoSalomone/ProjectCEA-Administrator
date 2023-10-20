package it.dedagroup.project_cea.facade;


import java.util.List;

import it.dedagroup.project_cea.dto.request.*;
import it.dedagroup.project_cea.dto.response.ApartmentScanDTOResponse;
import it.dedagroup.project_cea.mapper.*;
import it.dedagroup.project_cea.model.Apartment;
import it.dedagroup.project_cea.model.Condominium;
import it.dedagroup.project_cea.model.Scan;
import it.dedagroup.project_cea.service.impl.ApartmentServiceImpl;
import it.dedagroup.project_cea.service.impl.ScanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dedagroup.project_cea.dto.response.AdministratorDtoResponse;
import it.dedagroup.project_cea.dto.response.CondominiumDtoResponse;
import it.dedagroup.project_cea.dto.response.CustomerExtendedInfoDTOResponse;
import it.dedagroup.project_cea.model.Administrator;
import it.dedagroup.project_cea.service.def.BillServiceDef;
import it.dedagroup.project_cea.service.impl.AdministratorServiceImpl;
import it.dedagroup.project_cea.service.impl.CondominiumServiceImpl;

@Service
public class AdministratorFacade {
		
	@Autowired
	AdministratorServiceImpl service;
	@Autowired
	AdministratorMapper mapper;
	@Autowired
	CondominiumServiceImpl condominiumService;
	@Autowired
	BillServiceDef billService;
	@Autowired
	BillMapper billMapper;
	@Autowired
	CondominiumMapper condominiumMapper;
	@Autowired
	CustomerMapper customerMapper;
	@Autowired
	ApartmentServiceImpl apartmentService;
	@Autowired
	ApartmentMapper apartmentMapper;
	@Autowired
	ScanServiceImpl scanService;
	@Autowired
	ScanMapper scanMapper;

	
	public AdministratorDtoResponse findById(long id) {
		if(id<0) throw new RuntimeException("L'id deve essere maggiore di 0");
		Administrator a=service.findById(id);
		return mapper.toDto(a);
	}
	
	public AdministratorDtoResponse addAdministrator(RegisterUserDTORequest request) {
		Administrator a=new Administrator();
		a.setName(request.getName());
		a.setSurname(request.getSurname());
		a.setPassword(request.getPassword());
		a.setUsername(request.getUsername());
		a.setAvailable(true);
		return mapper.toDto(service.addAdministrator(a));
	}
	
	public AdministratorDtoResponse updateAdministrator(AdministratorUpdateDTORequest request) {
		Administrator a=service.findById(request.getId());
		if(request.getUsername()!=null) a.setUsername(request.getUsername());
		if(request.getPassword()!=null) a.setPassword(request.getPassword());
		return mapper.toDto(service.updateAdministrator(a));
	}
	
	public AdministratorDtoResponse findByCondominiums_Id(long id) {
		if(id<0) throw new RuntimeException("L'id non può essere minore di 0");
		return mapper.toDto(service.findByCondominiums_Id(id));
	}
	
	public void billSplitter() {
		
	}

	public List<CondominiumDtoResponse> getCondominiumByAdministratorId(AdministratorIdDtoRequest request){
			return condominiumMapper.toListDto(condominiumService.findCondominiumByAdministrator_id(request.getId()));
	}
	
	public String insertCondominium(CondominiumDTORequest dto) {
		condominiumService.addCondominium(condominiumMapper.toCondominium(dto));
		return "Condominio aggiunto con successo";
	}
	
	public String insertBill(BillDTORequest dto) {
		billService.addBill(billMapper.toBill(dto));
		return "bolletta inserita con successo";
	}
	
	public List<CustomerExtendedInfoDTOResponse> getCustomerByCondominiumId(long condominiumId) {
		return customerMapper.toListCustomersExtendedinfo(condominiumService.getConsumersByCondominiumId(condominiumId));
	}

	public void addApartment(AddApartmentDtoRequest request){
		apartmentService.saveApartment(apartmentMapper.fromAddApartmentDtoRequestToApartment(request));
	}

	public List<ApartmentScanDTOResponse> findAllScanByCondominiumId(long condominiumId){
		return scanMapper.toApartmentScanDtoResposneList(scanService.findAllScanByCondominiumId(condominiumId));
	}

	public void createCondominium(AddCondominiumDTORequest request){
		Condominium condominium=condominiumMapper.fromAddCondominiumDTORequestToCondominium(request);
		condominiumService.addCondominium(condominium);




	}


}
