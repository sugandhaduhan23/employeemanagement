import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  headers = {'Content-Type' : 'application/json'};

  constructor(private httpService: HttpService) { }

  createNewProject(data: any){
    return this.httpService.post(environment.RESTURL.SERVER+environment.RESTURL.PROJECT, data, this.headers);
  }

  updateProject(data: any){
    return this.httpService.put(environment.RESTURL.SERVER+environment.RESTURL.PROJECT, data, this.headers);
  }

  deleteProject(data: number){
    return this.httpService.delete(environment.RESTURL.SERVER+environment.RESTURL.PROJECT+"/"+data, undefined);
  }

  mapResource(data: any){
    return this.httpService.put(environment.RESTURL.SERVER+environment.RESTURL.MAP_PROJECT, data, this.headers );
  }

  unmapResource(data: any){
    return this.httpService.put(environment.RESTURL.SERVER+environment.RESTURL.UNMAP_PROJECT, data, this.headers);
  }

  fetchProjectTeam(data: number){
    return this.httpService.get(environment.RESTURL.SERVER+environment.RESTURL.PROJECT_TEAM+data, undefined);

  }

}
