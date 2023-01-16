//package com.sekim.uriseoroapi.uriseoruapi.service.serviceImpl;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    final UserDetailRepository udRepository;
//    final
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return udRepository.getUsername(email);
//    }
//}
