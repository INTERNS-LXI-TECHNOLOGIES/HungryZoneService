import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFoodItem } from '../food-item.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../food-item.test-samples';

import { FoodItemService } from './food-item.service';

const requireRestSample: IFoodItem = {
  ...sampleWithRequiredData,
};

describe('FoodItem Service', () => {
  let service: FoodItemService;
  let httpMock: HttpTestingController;
  let expectedResult: IFoodItem | IFoodItem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FoodItemService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a FoodItem', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const foodItem = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(foodItem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FoodItem', () => {
      const foodItem = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(foodItem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FoodItem', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FoodItem', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FoodItem', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFoodItemToCollectionIfMissing', () => {
      it('should add a FoodItem to an empty array', () => {
        const foodItem: IFoodItem = sampleWithRequiredData;
        expectedResult = service.addFoodItemToCollectionIfMissing([], foodItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(foodItem);
      });

      it('should not add a FoodItem to an array that contains it', () => {
        const foodItem: IFoodItem = sampleWithRequiredData;
        const foodItemCollection: IFoodItem[] = [
          {
            ...foodItem,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFoodItemToCollectionIfMissing(foodItemCollection, foodItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FoodItem to an array that doesn't contain it", () => {
        const foodItem: IFoodItem = sampleWithRequiredData;
        const foodItemCollection: IFoodItem[] = [sampleWithPartialData];
        expectedResult = service.addFoodItemToCollectionIfMissing(foodItemCollection, foodItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(foodItem);
      });

      it('should add only unique FoodItem to an array', () => {
        const foodItemArray: IFoodItem[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const foodItemCollection: IFoodItem[] = [sampleWithRequiredData];
        expectedResult = service.addFoodItemToCollectionIfMissing(foodItemCollection, ...foodItemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const foodItem: IFoodItem = sampleWithRequiredData;
        const foodItem2: IFoodItem = sampleWithPartialData;
        expectedResult = service.addFoodItemToCollectionIfMissing([], foodItem, foodItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(foodItem);
        expect(expectedResult).toContain(foodItem2);
      });

      it('should accept null and undefined values', () => {
        const foodItem: IFoodItem = sampleWithRequiredData;
        expectedResult = service.addFoodItemToCollectionIfMissing([], null, foodItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(foodItem);
      });

      it('should return initial array if no FoodItem is added', () => {
        const foodItemCollection: IFoodItem[] = [sampleWithRequiredData];
        expectedResult = service.addFoodItemToCollectionIfMissing(foodItemCollection, undefined, null);
        expect(expectedResult).toEqual(foodItemCollection);
      });
    });

    describe('compareFoodItem', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFoodItem(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFoodItem(entity1, entity2);
        const compareResult2 = service.compareFoodItem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFoodItem(entity1, entity2);
        const compareResult2 = service.compareFoodItem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFoodItem(entity1, entity2);
        const compareResult2 = service.compareFoodItem(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
