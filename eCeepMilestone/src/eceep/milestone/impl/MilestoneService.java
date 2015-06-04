package eceep.milestone.impl;

import java.util.List;

import eceep.milestone.Leaf;
import eceep.milestone.Milestone;
import eceep.milestone.Step;

/**
 * Milestone:  Step0 --- Step3 --- Step5 --- Step7
 * --------------------------------------------------------------------------
 * Steps tree: Step0 -+- Step1 --- Step2
 *                    +- Step3 -+- Step4
 *                    |         +- Step5 --- Step7
 *                    +- Step6
 * --------------------------------------------------------------------------                                      
 * @author Jack Jin
 *
 * @param <T>
 */
public class MilestoneService<T extends Step> implements Milestone<T> {
	protected Leaf<T> theFirstLeaf;
	
	private List<T> milestones;
	private int index;
	
	private List<T> steps;
	
	protected Leaf<T> getCurrentLeaf() {
		Leaf<T> ret = null;
		
		if (index >= 0 && index < milestones.size())
			ret = Find(theFirstLeaf, milestones.get(index));
		
		return ret;
	}
	
	@Override
	public List<T> getSteps() {
		return this.steps;
	}
	
	@Override
	public Milestone addChild(T parent, T child) {
        Milestone<T> ret = null;

        if (this.theFirstLeaf == null || parent == null) return ret;

        Leaf<T> leaf = Find(theFirstLeaf, parent);
        if (leaf != null)
        {
            Leaf<T> s = new Leaf<T>(leaf, child);
            if (s != null)
            {
                leaf.getChildren().add(s);

                if (this.steps.stream().filter(A -> A.getID()==child.getID()).findFirst() == null)
                    this.steps.add(child);

                ret = this;
            }
        }

        return ret;
	}

    public Leaf<T> Find(Leaf<T> currentLeaf, T step)
    {
        Leaf<T> ret = null;

        if (this._TheFirstLeaf == null || currentLeaf == null) return null;

        if (currentLeaf.Step == step)
        {
            ret = currentLeaf;
        }
        else
        {
            foreach (var item in currentLeaf.Children)
            {
                Leaf<T> s = Find(item, step);
                if (s != null)
                {
                    ret = s;
                    break;
                }
            }
        }

        return ret;
    }



    
    
    @Override
	public T next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T prev() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T goTop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T go(T step) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getCurrentStep() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean activeCurrent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inactiveCurrent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Milestone<T> deserialize(byte[] binary) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
    public Leaf<T> TheFirstLeaf { get { return _TheFirstLeaf; } }

    public IList<T> Milestones { get { return _Milestones; } }
    public int Index { get { return _Index; } set { if (value >= 0 && value < _Milestones.Count) _Index = value; } }
    public T CurrentStep { get { return _Milestones[_Index]; } }
    public bool IsActive { get { return _Index >= 0 && _Index < _Milestones.Count && CurrentLeaf.Step.Value4; } }


    /* Constructer*/
    /* ----------------------------------------------------- */
    public Milestone()
    {
        this._Steps = new List<T>();
    }

    /* Methods */
    /* ----------------------------------------------------- */
    public IMilestone<T> AddFirst(T step)
    {
        IMilestone<T> ret = this;

        _TheFirstLeaf = Leaf<T>.Create(null, step);

        if (_TheFirstLeaf != null)
        {
            _Milestones = new List<T>();
            _Milestones.Add(_TheFirstLeaf.Step);

            _Steps = new List<T>();
            _Steps.Add(step);

            _Index = -1;
        }

        return ret;
    }

    public bool RemoveChild(T step)
    {
        if (_TheFirstLeaf == null) return false;

        if (_TheFirstLeaf.Step == step)
        {
            this._TheFirstLeaf = null;
            this._Index = -1;

            return true;
        }

        bool result = false;
        Leaf<T> leaf = Find(_TheFirstLeaf, step);
        if (leaf != null)
        {
            Leaf<T> parent = leaf.Parent;
            parent.Children.Remove(leaf);

            ReloadMilestone(parent.Step);
            this._Index = this._Milestones.IndexOf(parent.Step);

            result = true;
        }

        return result;
    }

    public T GoTop()
    {
        T ret = null;

        if (this._TheFirstLeaf == null) return ret;

        bool result = this.ReloadMilestone(this._TheFirstLeaf.Step);
        if (result)
        {
            this._Index = 0;

            ret = CurrentLeaf.Step;
        }

        return ret;
    }

    public T Go(T step)
    {
        T ret = null;

        bool result = this.ReloadMilestone(step);
        if (result)
        {
            this._Index = this._Milestones.IndexOf(step);

            ret = this.CurrentLeaf.Step;
        }

        return ret;
    }

    public T Next()
    {
        return this.Next(null);
    }

    public T Next(T step)
    {
        T ret = null;

        if (this._TheFirstLeaf == null) return ret;

        if (this._Milestones == null || this._Milestones.Count <= 0) return ret;

        // Find next step
        Leaf<T> leaf = null;
        if (step == null)
        {
            if (CurrentLeaf != null && CurrentLeaf.Children != null && CurrentLeaf.Children.Count > 0)
            {
                leaf = CurrentLeaf.Children[0];
            }
        }
        else if (this.CurrentLeaf.Step == step)
        {
            leaf = this.CurrentLeaf;
        }
        else
        {
            for (int i = 0; CurrentLeaf != null && CurrentLeaf.Children != null && i < CurrentLeaf.Children.Count; i++)
            {
                if (CurrentLeaf.Children[i].Step == step)
                {
                    leaf = CurrentLeaf.Children[i];

                    break;
                }
            }
        }

        // if found, reload milestone & set index
        if (leaf != null && ReloadMilestone(leaf.Step))
        {
            ret = leaf.Step;

            this._Index = this._Milestones.IndexOf(leaf.Step);
        }

        return ret;
    }

    public T Prev()
    {
        if (this._TheFirstLeaf == null) return null;

        if (_Index >= 0) _Index--;

        return (this.CurrentLeaf != null) ? this.CurrentLeaf.Step : null;
    }

    public bool ActiveCurrent()
    {
        bool ret = false;

        if (_Index >= 0 && _Index < _Milestones.Count)
        {
            //Deactive(CurrentLeaf);
            CurrentLeaf.Step.Value4 = true;
        }

        return ret;
    }

    public bool DeactiveCurrent()
    {
        bool ret = false;

        if (_Index >= 0 && _Index < _Milestones.Count)
        {
            Deactive(CurrentLeaf);
            ret = true;
        }

        return ret;
    }

    public bool ReloadMilestone(T step)
    {
        bool result = false;

        // Find step by name
        Leaf<T> s = Find(this._TheFirstLeaf, step);
        if (s == null) return result;

        // Find this branch's last one of someone step by step
        Leaf<T> last = GetLastStep(s);
        if (last == null) return result;

        // Generate milestone
        IList<T> milestone = new List<T>();
        result = GetMilestone(last, milestone);
        if (result && (milestone != null && milestone.Count > 0))
        {
            this._Milestones = new List<T>();
            for (int i = milestone.Count - 1; i >= 0; i--)
            {
                this._Milestones.Add(milestone[i]);
            }
        }

        return result;
    }

    public T GetPrev()
    {
        if (_Milestones == null || _Milestones.Count <= 0) return null;

        int i = 0;
        if (_Index > 0)
            i = _Index - 1;

        return _Milestones[i];
    }

    public T GetNext()
    {
        if (_Milestones == null || _Milestones.Count <= 0) return null;

        int i = 0;
        i = _Index + 1;

        if (i >= _Milestones.Count)
            return null;

        return _Milestones[i];
    }

    public virtual byte[] Serialize()
    {
        byte[] binaryMilestone = null;
        try
        {
            using (MemoryStream mStream = new MemoryStream())
            {
                BinaryFormatter bFormatter = new BinaryFormatter();
                bFormatter.Serialize(mStream, this);
                binaryMilestone = mStream.ToArray();
            }
        }
        catch (Exception ex)
        {
        }

        return binaryMilestone;
    }

    public virtual IMilestone<T> Deserialize(byte[] binary)
    {
        IMilestone<T> result = null;

        if (binary != null && binary.Length > 0)
        {
            try
            {
                using (MemoryStream mStream = new MemoryStream())
                {
                    mStream.Write(binary, 0, binary.Length);

                    BinaryFormatter bFormatter = new BinaryFormatter();
                    mStream.Position = 0;
                    result = (IMilestone<T>)bFormatter.Deserialize(mStream);
                }
            }
            catch (Exception ex)
            {
                result = null;
            }
        }

        return result;
    }

    /* Functions */
    /* ----------------------------------------------------- */
    protected bool Deactive(Leaf<T> leaf)
    {
        if (this._TheFirstLeaf == null || leaf == null) return false;

        leaf.Step.Value4 = false;

        for (int i = 0; leaf.Children != null && i < leaf.Children.Count; i++)
        {
            Deactive(leaf.Children[i]);
        }

        return true;
    }

    protected Leaf<T> GetLastStep(Leaf<T> midLeaf)
    {
        if (this._TheFirstLeaf == null || midLeaf == null) return null;

        Leaf<T> result = null;

        if (midLeaf.Children == null || midLeaf.Children.Count <= 0)
        {
            result = midLeaf;
        }
        else
        {
            for (int i = 0; i < midLeaf.Children.Count; i++)
            {
                Leaf<T> s = GetLastStep(midLeaf.Children[i]);
                if (s != null)
                {
                    result = s;
                    break;
                }
            }
        }

        return result;
    }

    protected bool GetMilestone(Leaf<T> leaf, IList<T> milestone)
    {
        if (this._TheFirstLeaf == null || leaf == null) return false;

        milestone.Add(leaf.Step);

        if (leaf.Parent != null)
        {
            return GetMilestone(leaf.Parent, milestone);
        }

        return true;
    }
	
	
}
