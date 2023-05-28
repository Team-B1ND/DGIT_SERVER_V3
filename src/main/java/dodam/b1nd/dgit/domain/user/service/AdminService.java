package dodam.b1nd.dgit.domain.user.service;

import dodam.b1nd.dgit.domain.user.domain.entity.Admin;
import dodam.b1nd.dgit.domain.user.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
}
