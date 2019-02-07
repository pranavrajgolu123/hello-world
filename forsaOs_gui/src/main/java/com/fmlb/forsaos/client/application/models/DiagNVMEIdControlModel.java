package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;

public class DiagNVMEIdControlModel implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int vid;

	private int ssvid;

	private int ieee;

	private int mdts;

	private int cntlid;

	private Long ver;

	private int wctemp;

	private int cctemp;

	private Long tnvmcap;

	private int sqes;

	private int cqes;

	private int vwc;

	private int nvscc;

	private int frmw;

	public DiagNVMEIdControlModel()
	{
		super();
	}

	public DiagNVMEIdControlModel( int vid, int ssvid, int ieee, int mdts, int cntlid, Long ver, int wctemp, int cctemp, Long tnvmcap, int sqes, int cqes, int vwc, int nvscc, int frmw )
	{
		super();
		this.vid = vid;
		this.ssvid = ssvid;
		this.ieee = ieee;
		this.mdts = mdts;
		this.cntlid = cntlid;
		this.ver = ver;
		this.wctemp = wctemp;
		this.cctemp = cctemp;
		this.tnvmcap = tnvmcap;
		this.sqes = sqes;
		this.cqes = cqes;
		this.vwc = vwc;
		this.nvscc = nvscc;
		this.frmw = frmw;
	}

	public int getVid()
	{
		return vid;
	}

	public void setVid( int vid )
	{
		this.vid = vid;
	}

	public int getSsvid()
	{
		return ssvid;
	}

	public void setSsvid( int ssvid )
	{
		this.ssvid = ssvid;
	}

	public int getIeee()
	{
		return ieee;
	}

	public void setIeee( int ieee )
	{
		this.ieee = ieee;
	}

	public int getMdts()
	{
		return mdts;
	}

	public void setMdts( int mdts )
	{
		this.mdts = mdts;
	}

	public int getCntlid()
	{
		return cntlid;
	}

	public void setCntlid( int cntlid )
	{
		this.cntlid = cntlid;
	}

	public Long getVer()
	{
		return ver;
	}

	public void setVer( Long ver )
	{
		this.ver = ver;
	}

	public int getWctemp()
	{
		return wctemp;
	}

	public void setWctemp( int wctemp )
	{
		this.wctemp = wctemp;
	}

	public int getCctemp()
	{
		return cctemp;
	}

	public void setCctemp( int cctemp )
	{
		this.cctemp = cctemp;
	}

	public Long getTnvmcap()
	{
		return tnvmcap;
	}

	public void setTnvmcap( Long tnvmcap )
	{
		this.tnvmcap = tnvmcap;
	}

	public int getSqes()
	{
		return sqes;
	}

	public void setSqes( int sqes )
	{
		this.sqes = sqes;
	}

	public int getCqes()
	{
		return cqes;
	}

	public void setCqes( int cqes )
	{
		this.cqes = cqes;
	}

	public int getVwc()
	{
		return vwc;
	}

	public void setVwc( int vwc )
	{
		this.vwc = vwc;
	}

	public int getNvscc()
	{
		return nvscc;
	}

	public void setNvscc( int nvscc )
	{
		this.nvscc = nvscc;
	}

	public int getFrmw()
	{
		return frmw;
	}

	public void setFrmw( int frmw )
	{
		this.frmw = frmw;
	}

}
