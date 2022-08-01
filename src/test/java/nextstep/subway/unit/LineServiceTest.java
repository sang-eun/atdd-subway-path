package nextstep.subway.unit;

import nextstep.subway.applicaion.LineService;
import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class LineServiceTest {
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private LineService lineService;

    @Test
    void addSection() {
        // given
        // stationRepository와 lineRepository를 활용하여 초기값 셋팅
        Station 양재역 = stationRepository.save(new Station("양재역"));
        Station 양재시민의숲역 = stationRepository.save(new Station("양재시민의숲역"));
        Station 청계산입구역 = stationRepository.save(new Station("청계산입구역"));
        Line 분당선 = lineRepository.save(new Line("분당선", "red"));
        분당선.addSection(new Section(분당선, 양재역, 양재시민의숲역, 10));
        SectionRequest sectionRequest =  new SectionRequest(양재시민의숲역.getId(), 청계산입구역.getId(), 10);

        // when
        // lineService.addSection 호출
        lineService.addSection(분당선.getId(), sectionRequest);

        // then
        // line.getSections 메서드를 통해 검증
        assertThat(분당선.getSections()).hasSize(2);
        assertThat(분당선.getStations()).contains(양재역, 양재시민의숲역, 청계산입구역);
    }
}
