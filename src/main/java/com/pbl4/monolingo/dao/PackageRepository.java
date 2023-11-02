package com.pbl4.monolingo.dao;

import com.pbl4.monolingo.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package, Integer> {
}
