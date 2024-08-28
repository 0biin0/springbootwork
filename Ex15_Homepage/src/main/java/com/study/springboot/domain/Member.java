package com.study.springboot.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member {
	@Id
	private String id;
	@NonNull
	private String password;
	@NonNull
	private String name;
	private String email;
	private LocalDate birthday;
	private String gender;
	private String phone;
	private String address;
	
	@CreatedDate
	@Column(name="create_date")
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(name="update_date")
	private LocalDateTime updateDate;
}
/*
@Data:
출처: Lombok 라이브러리
역할: 이 어노테이션을 클래스에 붙이면 Lombok이 자동으로 getter, setter, toString(), equals(), hashCode() 메서드를 생성해 줍니다. 코드의 boilerplate를 줄여주어 코드가 더 간결해집니다.

@NoArgsConstructor:
출처: Lombok 라이브러리
역할: 이 어노테이션을 사용하면 매개변수가 없는 기본 생성자를 자동으로 생성해 줍니다. JPA와 같은 프레임워크에서는 엔티티 클래스에 기본 생성자가 필요할 때 유용합니다.

@Entity:
출처: JPA (Java Persistence API)
역할: 이 어노테이션을 클래스에 붙이면 해당 클래스가 JPA 엔티티로 인식됩니다. 즉, 이 클래스의 인스턴스는 데이터베이스 테이블의 행(row)으로 매핑됩니다.

@EntityListeners(AuditingEntityListener.class):
출처: JPA
역할: 이 어노테이션은 엔티티의 생명 주기 이벤트를 처리하는 리스너를 지정합니다. AuditingEntityListener는 엔티티의 생성 및 수정 시점을 자동으로 추적하고 기록하는 데 사용됩니다. 예를 들어, @CreatedDate와 @LastModifiedDate와 함께 사용되어 엔티티의 생성 시간과 수정 시간을 자동으로 관리할 수 있습니다.

@Id:
출처: JPA (Java Persistence API)
역할: 이 어노테이션은 엔티티 클래스의 필드를 데이터베이스 테이블의 기본 키로 지정합니다. 이 필드는 엔티티의 각 인스턴스를 고유하게 식별하는 데 사용됩니다.

@NonNull:
출처: Lombok 라이브러리
역할: 이 어노테이션을 필드에 붙이면 해당 필드에 대한 null 값 체크를 강제합니다. Lombok이 자동으로 null 검사를 수행하여, null 값이 들어가면 예외를 발생시킵니다. 보통 @NonNull은 메서드 파라미터에 사용되지만, 필드에 붙여서도 사용됩니다.

@CreatedDate:
출처: Spring Data JPA
역할: 이 어노테이션은 엔티티가 생성된 날짜와 시간을 자동으로 기록할 필드에 붙입니다. @EntityListeners(AuditingEntityListener.class)와 함께 사용되며, 엔티티가 데이터베이스에 저장될 때 createdDate 필드에 현재 날짜와 시간이 자동으로 설정됩니다.

@Column:
출처: JPA (Java Persistence API)
역할: 이 어노테이션은 엔티티 클래스의 필드를 데이터베이스 테이블의 열(column)에 매핑합니다. 필드의 데이터베이스 열 이름, 길이, null 허용 여부 등을 지정할 수 있습니다. 기본적으로 필드 이름이 열 이름으로 사용되지만, 이 어노테이션을 사용하여 열의 세부 속성을 조정할 수 있습니다.

@LastModifiedDate:
출처: Spring Data JPA
역할: 이 어노테이션은 엔티티가 마지막으로 수정된 날짜와 시간을 자동으로 기록할 필드에 붙입니다. @CreatedDate와 유사하게 @EntityListeners(AuditingEntityListener.class)와 함께 사용되며, 엔티티가 수정될 때 lastModifiedDate 필드에 현재 날짜와 시간이 자동으로 업데이트됩니다.




*/