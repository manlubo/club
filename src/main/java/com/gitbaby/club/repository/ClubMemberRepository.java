package com.gitbaby.club.repository;

import com.gitbaby.club.domain.entity.ClubMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
  @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
  @Query("select m from ClubMember m where m.fromSocial = :social and m.email = :email")
  Optional<ClubMember> findByEmail(String email, boolean social);


  @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
  Optional<ClubMember> findByEmailAndFromSocial(String email, boolean fromSocial);
}
