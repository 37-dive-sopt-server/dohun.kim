package org.sopt.member.repository.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.sopt.member.domain.Gender;
import org.sopt.member.domain.Member;

public class MemberFileStorage {

    private static final String DELIMITER = ",";

    private final Path storagePath;

    public MemberFileStorage(String directory, String fileName) {
        this.storagePath = Paths.get(directory, fileName);
        ensureDirectoryExists();
    }

    public List<Member> loadAll() {
        if (Files.notExists(storagePath)) {
            return List.of();
        }
        try (BufferedReader reader = Files.newBufferedReader(storagePath, StandardCharsets.UTF_8)) {
            return reader.lines()
                    .filter(line -> !line.isBlank())
                    .map(this::deserialize)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("회원 데이터를 불러오는 데 실패했습니다.", e);
        }
    }

    public void writeAll(Collection<Member> members) {
        List<String> lines = members.stream()
                .sorted(Comparator.comparingLong(Member::getId))
                .map(this::serialize)
                .collect(Collectors.toList());
        try {
            Files.write(storagePath, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new IllegalStateException("회원 데이터를 저장하는 데 실패했습니다.", e);
        }
    }

    private Optional<Member> deserialize(String line) {
        String[] tokens = line.split(DELIMITER, -1);
        if (tokens.length != 5) {
            return Optional.empty();
        }
        try {
            Long id = Long.parseLong(tokens[0]);
            String name = tokens[1];
            String email = tokens[2];
            Gender gender = Gender.valueOf(tokens[3]);
            LocalDate birthDate = LocalDate.parse(tokens[4]);
            return Optional.of(Member.createWithId(id, name, email, gender, birthDate));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private String serialize(Member member) {
        return String.join(DELIMITER,
                String.valueOf(member.getId()),
                member.getName(),
                member.getEmail(),
                member.getGender().name(),
                member.getBirthDate().toString());
    }

    private void ensureDirectoryExists() {
        Path parent = storagePath.getParent();
        if (parent != null && Files.notExists(parent)) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw new IllegalStateException("회원 데이터 디렉터리를 생성할 수 없습니다.", e);
            }
        }
    }
}
