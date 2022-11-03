import dayjs from 'dayjs/esm';

import { IFood, NewFood } from './food.model';

export const sampleWithRequiredData: IFood = {
  id: 57781,
  name: 'SCSI one-to-one benchmark',
  expiry: dayjs('2022-08-16T08:14'),
};

export const sampleWithPartialData: IFood = {
  id: 48845,
  name: 'Congolese',
  expiry: dayjs('2022-08-16T02:47'),
  imageUrl: 'optical',
};

export const sampleWithFullData: IFood = {
  id: 1164,
  name: 'Refined local',
  expiry: dayjs('2022-08-16T10:03'),
  description: 'navigate Small',
  imageUrl: 'SSL red Yemen',
};

export const sampleWithNewData: NewFood = {
  name: 'Lilangeni program port',
  expiry: dayjs('2022-08-16T08:39'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
