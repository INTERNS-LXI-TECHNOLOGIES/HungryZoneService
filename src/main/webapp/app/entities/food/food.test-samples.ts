import dayjs from 'dayjs/esm';

import { IFood, NewFood } from './food.model';

export const sampleWithRequiredData: IFood = {
  id: 57781,
  name: 'SCSI one-to-one benchmark',
  expiry: dayjs('2022-08-16T08:14'),
  remainingQty: 27072,
};

export const sampleWithPartialData: IFood = {
  id: 15544,
  name: 'Shoes Jewelery TCP',
  expiry: dayjs('2022-08-16T08:55'),
  remainingQty: 85508,
  description: 'bypassing Wyoming',
};

export const sampleWithFullData: IFood = {
  id: 64969,
  name: 'wireless Music Cheese',
  expiry: dayjs('2022-08-16T05:14'),
  remainingQty: 38802,
  description: 'Baby',
  imageUrl: 'Spain quantify Object-based',
};

export const sampleWithNewData: NewFood = {
  name: 'primary',
  expiry: dayjs('2022-08-16T10:03'),
  remainingQty: 56299,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
