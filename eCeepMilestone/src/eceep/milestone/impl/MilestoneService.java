package eceep.milestone.impl;

import java.util.ArrayList;
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
 */
public class MilestoneService<T extends Step> implements Milestone<T> {
	public static <G extends Step> Milestone<G> getInstance() {
		MilestoneService<G> result = new MilestoneService<G>();
		
		return result;
	}
		
	protected Leaf<T> theFirstLeaf;
	
	private List<T> milestones;
	private int index;
	
	private List<T> steps;
	
	public MilestoneService(){
		this.steps = new ArrayList<T>();
	}
	
	/* Get properties -------------------------------------------- */
	@Override
	public Leaf<T> getTheFirstLeaf() {
		return this.theFirstLeaf;
	}
	
	@Override
	public Leaf<T> getCurrentLeaf() {
		Leaf<T> ret = null;
		
		if (this.index >= 0 && this.index < milestones.size())
			ret = find(theFirstLeaf, milestones.get(this.index));
		
		return ret;
	}
	
	@Override
	public List<T> getSteps() {
		return this.steps;
	}
	
	@Override
	public List<T> getMilestones() {
		return this.milestones;
	}
	
	@Override
	public boolean isActive() {
		return this.index>=0 && this.index < this.milestones.size() && this.getCurrentLeaf().getStep().isActive();
	}
	/* ------------------------------------------------------------ */
	
	/* Manipulate methods ----------------------------------------- */
	@Override
    public Milestone<T> addFirst(T step)
    {
        Milestone<T> ret = this;

        this.theFirstLeaf = new Leaf<T>(null, step);

        if (this.theFirstLeaf != null)
        {
            this.milestones = new ArrayList<T>();
            this.milestones.add(this.theFirstLeaf.getStep());

            this.steps = new ArrayList<T>();
            this.steps.add(step);

            this.index = -1;
        }

        return ret;
    }

	@Override
	public Milestone<T> addChild(T parent, T child) {
        Milestone<T> ret = null;

        if (this.theFirstLeaf == null || parent == null) return ret;

        Leaf<T> leaf = this.find(theFirstLeaf, parent);
        if (leaf != null)
        {
            Leaf<T> s = new Leaf<T>(leaf, child);
            if (s != null)
            {
                leaf.getChildren().add(s);

                if (this.steps.stream().filter(A -> A.getId()==child.getId()).findFirst() == null)
                    this.steps.add(child);

                ret = this;
            }
        }

        return ret;
	}

	@Override
    public boolean activeCurrent()
    {
        boolean ret = false;

        if (this.index >= 0 && this.index < this.milestones.size())
        {
            //inactive(CurrentLeaf);
            getCurrentLeaf().getStep().setActive(true);
        }

        return ret;
    }

	@Override
    public boolean inactiveCurrent()
    {
        boolean ret = false;

        if (this.index >= 0 && this.index < this.milestones.size())
        {
            inactive(getCurrentLeaf());
            ret = true;
        }

        return ret;
    }

	@Override
    public boolean reloadMilestone(T step)
    {
        boolean result = false;

        // Find step by name
        Leaf<T> s = find(this.theFirstLeaf, step);
        if (s == null) return result;

        // Find this branch's last one of someone step by step
        Leaf<T> last = getLastStep(s);
        if (last == null) return result;

        // Generate milestone
        List<T> milestone = new ArrayList<T>();
        result = getMilestone(last, milestone);
        if (result && (milestone != null && milestone.size() > 0))
        {
            this.milestones = new ArrayList<T>();
            for (int i = milestone.size() - 1; i >= 0; i--)
            {
                this.milestones.add(milestone.get(i));
            }
        }

        return result;
    }

	@Override
    public byte[] serialize()
    {
        byte[] binaryMilestone = null;
//        try
//        {
//            using (MemoryStream mStream = new MemoryStream())
//            {
//                BinaryFormatter bFormatter = new BinaryFormatter();
//                bFormatter.Serialize(mStream, this);
//                binaryMilestone = mStream.ToArray();
//            }
//        }
//        catch (Exception ex)
//        {
//        }

        return binaryMilestone;
    }

	@Override
    public Milestone<T> deserialize(byte[] binary)
    {
        Milestone<T> result = null;

//        if (binary != null && binary.size() > 0)
//        {
//            try
//            {
//                using (MemoryStream mStream = new MemoryStream())
//                {
//                    mStream.Write(binary, 0, binary.Length);
//
//                    BinaryFormatter bFormatter = new BinaryFormatter();
//                    mStream.Position = 0;
//                    result = (IMilestone<T>)bFormatter.Deserialize(mStream);
//                }
//            }
//            catch (Exception ex)
//            {
//                result = null;
//            }
//        }

        return result;
    }

	/* ------------------------------------------------------------ */
	
    /* Navigation methods ----------------------------------------- */
	@Override
    public Leaf<T> find(Leaf<T> currentLeaf, T step)
    {
        Leaf<T> ret = null;

        if (this.theFirstLeaf == null || currentLeaf == null) return null;

        if (currentLeaf.getStep().getId() == step.getId())
        {
            ret = currentLeaf;
        }
        else
        {
            for (Leaf<T> item : currentLeaf.getChildren())
            {
                Leaf<T> s = find(item, step);
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
    public T goTop()
    {
        T ret = null;

        if (this.theFirstLeaf == null) return ret;

        boolean result = this.reloadMilestone(this.theFirstLeaf.getStep());
        if (result)
        {
            this.index = 0;

            ret = getCurrentLeaf().getStep();
        }

        return ret;
    }

	@Override
    public T go(T step)
    {
        T ret = null;

        boolean result = this.reloadMilestone(step);
        if (result)
        {
            this.index = this.milestones.indexOf(step);

            ret = this.getCurrentLeaf().getStep();
        }

        return ret;
    }
    
    @Override
	public T next() {
    	return this.Next(null);
	}

    @Override
    public T prev()
    {
        if (this.theFirstLeaf == null) return null;

        if (this.index >= 0) this.index--;

        return (this.getCurrentLeaf() != null) ? this.getCurrentLeaf().getStep() : null;
    }
    /* ------------------------------------------------------------ */
	
    protected boolean getMilestone(Leaf<T> leaf, List<T> milestone)
    {
        if (this.milestones == null || leaf == null) return false;

        milestone.add(leaf.getStep());

        if (leaf.getParent() != null)
        {
            return getMilestone(leaf.getParent(), milestone);
        }

        return true;
    }

    private T Next(T step)
    {
        T ret = null;

        if (this.theFirstLeaf == null) return ret;

        if (this.milestones == null || this.milestones.size() <= 0) return ret;

        // Find next step
        Leaf<T> leaf = null;
        if (step == null)
        {
            if (this.getCurrentLeaf() != null && this.getCurrentLeaf().getChildren() != null && this.getCurrentLeaf().getChildren().size() > 0)
            {
                leaf = this.getCurrentLeaf().getChildren().get(0);
            }
        }
        else if (this.getCurrentLeaf().getStep().getId() == step.getId())
        {
            leaf = this.getCurrentLeaf();
        }
        else
        {
            for (int i = 0; this.getCurrentLeaf() != null && this.getCurrentLeaf().getChildren() != null && i < this.getCurrentLeaf().getChildren().size(); i++)
            {
                if (this.getCurrentLeaf().getChildren().get(i).getStep().getId() == step.getId())
                {
                    leaf = this.getCurrentLeaf().getChildren().get(i);

                    break;
                }
            }
        }

        // if found, reload milestone & set index
        if (leaf != null && reloadMilestone(leaf.getStep()))
        {
            ret = leaf.getStep();

            this.index = this.milestones.indexOf(leaf.getStep());
        }

        return ret;
    }

    protected Leaf<T> getLastStep(Leaf<T> midLeaf)
    {
        if (this.milestones == null || midLeaf == null) return null;

        Leaf<T> result = null;

        if (midLeaf.getChildren() == null || midLeaf.getChildren().size() <= 0)
        {
            result = midLeaf;
        }
        else
        {
            for (int i = 0; i < midLeaf.getChildren().size(); i++)
            {
                Leaf<T> s = getLastStep(midLeaf.getChildren().get(i));
                if (s != null)
                {
                    result = s;
                    break;
                }
            }
        }

        return result;
    }

    protected boolean inactive(Leaf<T> leaf)
    {
        if (this.milestones == null || leaf == null) return false;

        leaf.getStep().setActive(false);

        for (int i = 0; leaf.getChildren() != null && i < leaf.getChildren().size(); i++)
        {
            inactive(leaf.getChildren().get(i));
        }

        return true;
    }


    
    
    /*
    public int getIndex() { 
    	 return this.index;
    }
    
    public void setIndex(int index) {
    	if (index >= 0 && index < this.milestones.size()) this.index = index;
    }
    
    public T getCurrentStep() { 
    	return this.milestones.get(this.index); 
    }

    public boolean RemoveChild(T step)
    {
        if (this.theFirstLeaf == null) return false;

        if (this.theFirstLeaf.getStep().getID() == step.getID())
        {
            this.theFirstLeaf = null;
            this.index = -1;

            return true;
        }

        boolean result = false;
        Leaf<T> leaf = find(this.theFirstLeaf, step);
        if (leaf != null)
        {
            Leaf<T> parent = leaf.getParent();
            parent.getChildren().remove(leaf);

            reloadMilestone(parent.getStep());
            this.index = this.milestones.indexOf(parent.getStep());

            result = true;
        }

        return result;
    }

    public T GetPrev()
    {
        if (this.milestones == null || this.milestones.size() <= 0) return null;

        int i = 0;
        if (this.index > 0)
            i = this.index - 1;

        return this.milestones.get(i);
    }

    public T GetNext()
    {
        if (this.milestones == null || this.milestones.size() <= 0) return null;

        int i = 0;
        i = this.index + 1;

        if (i >= this.milestones.size())
            return null;

        return this.milestones.get(i);
    }

    */	
}
