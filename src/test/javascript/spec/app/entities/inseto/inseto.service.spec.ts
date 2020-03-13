import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { InsetoService } from 'app/entities/inseto/inseto.service';
import { IInseto, Inseto } from 'app/shared/model/inseto.model';
import { TipoInseto } from 'app/shared/model/enumerations/tipo-inseto.model';

describe('Service Tests', () => {
  describe('Inseto Service', () => {
    let injector: TestBed;
    let service: InsetoService;
    let httpMock: HttpTestingController;
    let elemDefault: IInseto;
    let expectedResult: IInseto | IInseto[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InsetoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Inseto(0, TipoInseto.BARATA, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Inseto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Inseto()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Inseto', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            numero: 1,
            altura: 1,
            peso: 1,
            comprimento: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Inseto', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            numero: 1,
            altura: 1,
            peso: 1,
            comprimento: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Inseto', () => {
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
