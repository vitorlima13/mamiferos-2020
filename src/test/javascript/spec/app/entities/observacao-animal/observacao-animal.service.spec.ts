import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ObservacaoAnimalService } from 'app/entities/observacao-animal/observacao-animal.service';
import { IObservacaoAnimal, ObservacaoAnimal } from 'app/shared/model/observacao-animal.model';

describe('Service Tests', () => {
  describe('ObservacaoAnimal Service', () => {
    let injector: TestBed;
    let service: ObservacaoAnimalService;
    let httpMock: HttpTestingController;
    let elemDefault: IObservacaoAnimal;
    let expectedResult: IObservacaoAnimal | IObservacaoAnimal[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ObservacaoAnimalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ObservacaoAnimal(0, currentDate, 'AAAAAAA', 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ObservacaoAnimal', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );

        service.create(new ObservacaoAnimal()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ObservacaoAnimal', () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_TIME_FORMAT),
            local: 'BBBBBB',
            comprimento: 1,
            altura: 1,
            peso: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ObservacaoAnimal', () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_TIME_FORMAT),
            local: 'BBBBBB',
            comprimento: 1,
            altura: 1,
            peso: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ObservacaoAnimal', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
